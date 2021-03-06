/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.logisticawmj.wmj.domain.Cliente;
import br.com.logisticawmj.wmj.domain.ItemPedido;
import br.com.logisticawmj.wmj.domain.PagamentoComBoleto;
import br.com.logisticawmj.wmj.domain.Pedido;
import br.com.logisticawmj.wmj.domain.enums.EstadoPagamento;
import br.com.logisticawmj.wmj.repositorios.ItemPedidoRepositorio;
import br.com.logisticawmj.wmj.repositorios.PagamentoRepositorio;
import br.com.logisticawmj.wmj.repositorios.PedidoRepositorio;
import br.com.logisticawmj.wmj.security.UserSS;
import br.com.logisticawmj.wmj.services.exceptions.AuthorizationException;
import br.com.logisticawmj.wmj.services.exceptions.ObjectNotFoundException;

/**
 *
 * @author wellington
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepositorio repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private ItemPedidoRepositorio itemPedidoRepositorio;

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepositorio.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepositorio.saveAll(obj.getItens());
//        System.out.println(obj);
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
    
    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}

}
