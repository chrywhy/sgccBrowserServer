package com.chry.browserServer.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chry.util.cipher.DES;
import com.chry.util.security.Token;
import com.chry.util.security.TokenManager;

@RestController
@RequestMapping(value="/register")
public class ClientRigisterControl {
	static Logger logger = LogManager.getLogger(ClientRigisterControl.class.getName());

	@RequestMapping(value="/{ip}", method=RequestMethod.GET)
    public String register(@PathVariable String ip) {
		Token token = new Token(ip);
		TokenManager.add(ip, token);
    	String encryptToken = DES.encrypt(token.toString());
		logger.info("generated token: " + token);
		return encryptToken;
    }
}