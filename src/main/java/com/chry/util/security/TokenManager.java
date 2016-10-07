package com.chry.util.security;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chry.browserServer.Application;

public class TokenManager {
	static Logger logger = LogManager.getLogger(Application.class.getName());
	private static Map<String, Token> _tokens = new ConcurrentHashMap<String, Token>();
	private static ScheduledFuture<?> _detectSchedule;
    private static ScheduledExecutorService _executor = Executors.newScheduledThreadPool(1);
    
    private static long CHECK_INTERVAL = 30 * 60 * 1000; // 30 minutes
    
	protected static class Task implements Runnable {
		public Task() {
		}
		public void run() {
			logger.info("Check expired tokens...");
			Set<String> ids = _tokens.keySet();
			for(String id : ids) {
				Token token = _tokens.get(id);
				if (token.isExpired()) {
					_tokens.remove(id);
					logger.info("found expired token: id=" + id);
				}
			}
			logger.info("Check expired tokens done");
		}
	}
	
	public static void add(String id, Token token) {
		_tokens.put(id, token);
	}
		
	public static boolean check(String token) {
		for (Token existToken :  _tokens.values()) {
			if (existToken.toString().equals(token)) {
				return true;
			}
		}; 
		return false;
	}
	
	public static Token getToken(String id) {
		Token token = _tokens.get(id);
		if (token != null) {
			token.reNew();
		}
		return token;
	}
	
	public static void startCheck() {
		Task task = new Task();
		_detectSchedule = _executor.scheduleWithFixedDelay(task, CHECK_INTERVAL, CHECK_INTERVAL, TimeUnit.MILLISECONDS);
	}
	
	public static void stopCheck() {
		_detectSchedule.cancel(true);
	}
}