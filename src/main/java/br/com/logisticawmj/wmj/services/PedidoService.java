/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import br.com.logisticawmj.wmj.domain.Pedido;
import br.com.logisticawmj.wmj.repositorios.PedidoRepositorio;
import br.com.logisticawmj.wmj.services.exceptions.ObjectNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wellington
 */

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepositorio repo;
    
  public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
    
}
