/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services.validation;

import br.com.logisticawmj.wmj.domain.Cliente;
import br.com.logisticawmj.wmj.domain.enums.TipoCliente;
import br.com.logisticawmj.wmj.dto.ClienteNewDTO;
import br.com.logisticawmj.wmj.repositorios.ClienteRepositorio;
import br.com.logisticawmj.wmj.resource.exception.FieldMessage;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author desenv-01
 */
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    
    @Autowired
    private ClienteRepositorio repo;
    
    @Override
    public void initialize(ClienteInsert ann) {
        
    }
    
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context){
        List<FieldMessage> list = new ArrayList<>();
        
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && 
                !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CPF inválido"));            
        }
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && 
                !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","CNPJ inválido"));            
        }
        
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null){
            list.add(new FieldMessage("email","Email já existente"));
        }
        
        for (FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).
                    addPropertyNode(e.getFieldName()).
                    addConstraintViolation();
        }
        return list.isEmpty();
    }
    
}
