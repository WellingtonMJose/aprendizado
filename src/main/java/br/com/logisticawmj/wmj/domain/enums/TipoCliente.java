/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.domain.enums;

/**
 *
 * @author desenv-01
 */
public enum TipoCliente {
    
    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Juridica");
    
    private int cod;
    private String descricao;
    
   private TipoCliente(int cod, String descricao) {
       this.cod = cod;
       this.descricao = descricao;
   } 

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }
   
   public static TipoCliente toEnum(Integer cod) {
       if(cod == null){
           return null;
       }
       for(TipoCliente x: TipoCliente.values()){
           if(cod.equals(x.getCod())){
               return x;
           }
       }
       throw new IllegalArgumentException("Id inválido: " + cod);
   }
    
}
