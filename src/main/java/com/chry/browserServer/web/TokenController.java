package com.chry.browserServer.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chry.util.cipher.DES;
import com.chry.util.security.TokenManager;

@RequestMapping("/tokens")
public class TokenController {
	static Logger logger = LogManager.getLogger(ClientRigisterControl.class.getName());

	@RequestMapping(value="/{encryptToken}", method=RequestMethod.GET)
    public boolean verify(@PathVariable String encryptToken) {
		String token = DES.decrypt(encryptToken);
		boolean isOK =  TokenManager.check(token);
		logger.info("check encrypttoken: " + encryptToken + " is " + isOK);
		return isOK;
    }
}
