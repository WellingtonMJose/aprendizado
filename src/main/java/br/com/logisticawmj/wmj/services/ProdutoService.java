/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import br.com.logisticawmj.wmj.domain.Categoria;
import br.com.logisticawmj.wmj.domain.Produto;
import br.com.logisticawmj.wmj.repositorios.CategoriaRepositorio;
import br.com.logisticawmj.wmj.repositorios.ProdutoRepositorio;
import br.com.logisticawmj.wmj.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

/**
 *
 * @author wellington
 */
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepositorio repo;
    
    @Autowired
    private CategoriaRepositorio categoriaRepo;

    public Produto find(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }
    
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepo.findAllById(ids);
        return repo.findDistinctByNomeContainingIgnoreCaseAndCategoriasIn(nome, categorias, pageRequest);	
        
    }

}
