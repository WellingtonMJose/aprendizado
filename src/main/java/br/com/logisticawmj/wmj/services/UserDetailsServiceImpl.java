/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import br.com.logisticawmj.wmj.domain.Cliente;
import br.com.logisticawmj.wmj.repositorios.ClienteRepositorio;
import br.com.logisticawmj.wmj.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author desenv-01
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepositorio repo;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cli = repo.findByEmail(email);
        if(cli == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(cli.getId(), cli.getEmail(),cli.getSenha(), cli.getPerfis());
    }

}
