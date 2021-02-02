/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.logisticawmj.wmj.domain.Cidade;
import br.com.logisticawmj.wmj.domain.Cliente;
import br.com.logisticawmj.wmj.domain.Endereco;
import br.com.logisticawmj.wmj.domain.enums.Perfil;
import br.com.logisticawmj.wmj.domain.enums.TipoCliente;
import br.com.logisticawmj.wmj.dto.ClienteDTO;
import br.com.logisticawmj.wmj.dto.ClienteNewDTO;
import br.com.logisticawmj.wmj.repositorios.ClienteRepositorio;
import br.com.logisticawmj.wmj.repositorios.EnderecoRepositorio;
import br.com.logisticawmj.wmj.security.UserSS;
import br.com.logisticawmj.wmj.services.exceptions.AuthorizationException;
import br.com.logisticawmj.wmj.services.exceptions.DataIntegrityException;
import br.com.logisticawmj.wmj.services.exceptions.ObjectNotFoundException;

/**
 *
 * @author wellington
 */
@Service
public class ClienteService {
    
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClienteRepositorio repo;

    @Autowired
    private EnderecoRepositorio enderecoRepository;

    public Cliente find(Integer id) {
    	
    	UserSS user = UserService.authenticated();
    	if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
    		throw new AuthorizationException("Acesso negado");
    	}
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas.");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);

    }

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(
                null,
                objDto.getNome(),
                objDto.getEmail(),
                objDto.getCpfOuCnpj(),
                TipoCliente.toEnum(objDto.getTipo()),
                pe.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(
                null,
                objDto.getLogradouro(),
                objDto.getNumero(),
                objDto.getComplemento(),
                objDto.getBairro(),
                objDto.getCep(),
                cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
