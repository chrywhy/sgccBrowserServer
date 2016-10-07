package com.chry.browserServer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@EnableAutoConfiguration
public class BrowserController {
	
	@RequestMapping("/adminuser")
    public String getAdminUserView(){
		return "adminUser";
    }
	
	@RequestMapping("/adminsite")
    public String getAdminSiteView(){
		return "adminSite";
    }

	@RequestMapping("/restrict")
    public String getRistrictView(){
		return "restrict";
    }
}	
