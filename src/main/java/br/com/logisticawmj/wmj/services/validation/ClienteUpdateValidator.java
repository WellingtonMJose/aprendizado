/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services.validation;

import br.com.logisticawmj.wmj.domain.Cliente;
import br.com.logisticawmj.wmj.domain.enums.TipoCliente;
import br.com.logisticawmj.wmj.dto.ClienteDTO;
import br.com.logisticawmj.wmj.dto.ClienteNewDTO;
import br.com.logisticawmj.wmj.repositorios.ClienteRepositorio;
import br.com.logisticawmj.wmj.resource.exception.FieldMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

/**
 *
 * @author desenv-01
 */
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private ClienteRepositorio repo;
    
    @Override
    public void initialize(ClienteUpdate ann) {
        
    }
    
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context){
        
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));
        
        
        List<FieldMessage> list = new ArrayList<>();
        
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)){
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
