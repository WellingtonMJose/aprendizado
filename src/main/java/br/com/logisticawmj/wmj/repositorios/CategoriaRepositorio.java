/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.repositorios;

import br.com.logisticawmj.wmj.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author wellington
 */
@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer>{

    
}
