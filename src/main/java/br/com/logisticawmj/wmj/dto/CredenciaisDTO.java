/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.dto;

import java.io.Serializable;

/**
 *
 * @author desenv-01
 */
public class CredenciaisDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String email;
    private String senha;

    public CredenciaisDTO() {
    }

    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
}
