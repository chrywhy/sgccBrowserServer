package com.chry.browserServer;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.chry.browserServer.db.DbConnection;
import com.chry.util.security.TokenManager;

@Configuration
@EnableAutoConfiguration
@ComponentScan

public class Application extends SpringBootServletInitializer {
	static Logger logger = LogManager.getLogger(Application.class.getName());

	@Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
    public static void main(String[] args) throws Exception {
    	String host = "localhost";
    	String user = "root";
    	String password = "chrysam";
    	try {
	    	Console console = System.console();
	    	host = console.readLine("[%s]", "Mysql服务器地址(127.0.0.1):");
	    	if ("".equals(host.trim())) {
	    		host = "127.0.0.1";
	    	}
	    	user = console.readLine("[%s]", "用户名(root):");
	    	if ("".equals(user.trim())) {
	    		user = "root";
	    	}
	    	password="";
	    	char[] pwd = console.readPassword("[%s]", "请输入密码:");
	    	if(console != null && pwd != null){
	    		password = String.valueOf(pwd);  
	    	}
    	} catch(Exception e) {
    		host="127.0.0.1";
    		user="root";
    		password="chrysam";
    	}
    	DbConnection.init(host,user, password);
    	
    	
    	SpringApplication webApp = new SpringApplication(Application.class);
    	webApp.setBanner(new Banner() {
			@Override
			public void printBanner(Environment arg0, Class<?> arg1, PrintStream arg2) {
				 InputStream is = this.getClass().getResourceAsStream("/logo.txt");
				 try {
					System.out.println(IOUtils.toString(is,"UTF-8"));
				} catch (IOException e) {
					logger.error("cannot print logo", e);
				}
			}
    	});
    	webApp.run(args);
    	TokenManager.startCheck();
//    	SpringApplication.run(Application.class, args);
    }
}
