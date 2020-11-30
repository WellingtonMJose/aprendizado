/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import br.com.logisticawmj.wmj.domain.Categoria;
import br.com.logisticawmj.wmj.domain.Cidade;
import br.com.logisticawmj.wmj.domain.Cliente;
import br.com.logisticawmj.wmj.domain.Endereco;
import br.com.logisticawmj.wmj.domain.Estado;
import br.com.logisticawmj.wmj.domain.ItemPedido;
import br.com.logisticawmj.wmj.domain.Pagamento;
import br.com.logisticawmj.wmj.domain.PagamentoComBoleto;
import br.com.logisticawmj.wmj.domain.PagamentoComCartao;
import br.com.logisticawmj.wmj.domain.Pedido;
import br.com.logisticawmj.wmj.domain.Produto;
import br.com.logisticawmj.wmj.domain.enums.EstadoPagamento;
import br.com.logisticawmj.wmj.domain.enums.TipoCliente;
import br.com.logisticawmj.wmj.repositorios.CategoriaRepositorio;
import br.com.logisticawmj.wmj.repositorios.CidadeRepositorio;
import br.com.logisticawmj.wmj.repositorios.ClienteRepositorio;
import br.com.logisticawmj.wmj.repositorios.EnderecoRepositorio;
import br.com.logisticawmj.wmj.repositorios.EstadoRepositorio;
import br.com.logisticawmj.wmj.repositorios.ItemPedidoRepositorio;
import br.com.logisticawmj.wmj.repositorios.PagamentoRepositorio;
import br.com.logisticawmj.wmj.repositorios.PedidoRepositorio;
import br.com.logisticawmj.wmj.repositorios.ProdutoRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author desenv-01
 */
@Service
public class DBService {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @Autowired
    private CidadeRepositorio cidadeRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Autowired
    private ItemPedidoRepositorio itemPedidoRepositorio;

    public void instantiateTestDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "TI");
        Categoria cat2 = new Categoria(null, "Escritorio");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Automotivo");
        Categoria cat7 = new Categoria(null, "Perfumaria");
        Categoria cat8 = new Categoria(null, "Açougue");

        Produto p1 = new Produto(null, "Computador", 2000.0);
        Produto p2 = new Produto(null, "Mesa", 800.0);
        Produto p3 = new Produto(null, "Impressora", 450.0);
        Produto p4 = new Produto(null, "Cadeiras", 250.0);
        Produto p5 = new Produto(null, "Mouse", 80.0);
        Produto p6 = new Produto(null, "Toalha", 45.0);
        Produto p7 = new Produto(null, "TV Smart", 2000.0);
        Produto p8 = new Produto(null, "Roçadeira", 800.0);
        Produto p9 = new Produto(null, "Abajour", 100.0);
        Produto p10 = new Produto(null, "Shampoo", 12.0);
        Produto p11 = new Produto(null, "Contra-filé", 32.0);
        Produto p12 = new Produto(null, "Óleo de motor 20-w50", 32.0);
        Produto p13 = new Produto(null, "Colcha", 92.0);

        cat1.getListProdutos().addAll(Arrays.asList(p1, p2, p3, p4, p5));
        cat2.getListProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getListProdutos().addAll(Arrays.asList(p6, p13));
        cat4.getListProdutos().addAll(Arrays.asList(p7, p1, p9));
        cat5.getListProdutos().addAll(Arrays.asList(p8));
        cat6.getListProdutos().addAll(Arrays.asList(p12));
        cat7.getListProdutos().addAll(Arrays.asList(p10));
        cat8.getListProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        p4.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p5.getCategorias().addAll(Arrays.asList(cat1));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat4));
        p10.getCategorias().addAll(Arrays.asList(cat7));
        p11.getCategorias().addAll(Arrays.asList(cat8));
        p12.getCategorias().addAll(Arrays.asList(cat6));
        p13.getCategorias().addAll(Arrays.asList(cat3));

        categoriaRepositorio.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
        produtoRepositorio.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13));

        Estado est1 = new Estado(null, "São Paulo");
        Estado est2 = new Estado(null, "Minas Gerais");

        Cidade c1 = new Cidade(null, "Uberlandia", est2);
        Cidade c2 = new Cidade(null, "Campinas", est1);
        Cidade c3 = new Cidade(null, "São paulo", est1);
        Cidade c4 = new Cidade(null, "Montes Claros", est2);
        Cidade c5 = new Cidade(null, "Guarulhos", est1);

        est1.getCidades().addAll(Arrays.asList(c2, c3, c5));
        est2.getCidades().addAll(Arrays.asList(c1, c4));

        estadoRepositorio.saveAll(Arrays.asList(est1, est2));
        cidadeRepositorio.saveAll(Arrays.asList(c1, c2, c3, c4, c5));

        Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("27363323", "993838931"));

        Endereco e1 = new Endereco(null, "Rua Flores", "321", "Apto. 301", "Jardim", "3822034", cli1, c1);
        Endereco e2 = new Endereco(null, "Rua Campo de brito", "130", "Casa 1", "Pq. Maria Helena", "07261120", cli1, c5);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepositorio.saveAll(Arrays.asList(cli1));
        enderecoRepositorio.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2020 19:50"), cli1, e2);
        Pedido ped2 = new Pedido(null, sdf.parse("10/11/2020 09:33"), cli1, e1);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/11/2020 00:10"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepositorio.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepositorio.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepositorio.saveAll(Arrays.asList(ip1, ip2, ip3));
    }

}
