/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logisticawmj.wmj.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author desenv-01
 */
public class MockEmailService extends AbstractEmailService {
    
    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulando envio de email ...");
        LOG.info(msg.toString());
        LOG.info("Email enviado");
    }
    
}