package com.chry.browserServer.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chry.browserServer.db.DbConnection;
import com.chry.browserServer.db.model.ISiteDao;
import com.chry.browserServer.db.model.Site;
import com.chry.util.Helper;

@RestController
@RequestMapping(value="/sites")
public class SiteController {
	static Logger logger = LogManager.getLogger(SiteController.class.getName());
	
    @RequestMapping(value="", method=RequestMethod.GET)
    public List<Site> getSites() {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			logger.info("Get all sites");
			ISiteDao siteDao = session.getMapper(ISiteDao.class);
			List<Site> sites = new ArrayList<Site>();
			sites = siteDao.getAllSites();
			return sites;
		} finally {
			session.close();
		}
    }

    @RequestMapping(value="/types", method=RequestMethod.GET)
    public List<String> getSiteTypes() {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			logger.info("Get all site types: ");
			ISiteDao siteDao = session.getMapper(ISiteDao.class);
			List<String> siteTypes = new ArrayList<String>();
			siteTypes = siteDao.getAllTypes();
			return siteTypes;
		} finally {
			session.close();
		}
    }
    
    @RequestMapping(value="", method=RequestMethod.POST, headers = {"Content-type=application/json"})
    public Site addSite(@RequestBody @Valid Site site) {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			ISiteDao siteDao = session.getMapper(ISiteDao.class);
			siteDao.insertSite(site);
			session.commit();
			site = siteDao.getSiteByUrl(site.getPattern());
			logger.info("Add site: " + Helper.toJson(site));
			return site;
		} finally {
			session.close();
		}
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Site getSite(@PathVariable Integer id) {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			ISiteDao siteDao = session.getMapper(ISiteDao.class);
			Site site = siteDao.getSiteById(id);
			return site;
		} finally {
			session.close();
		}
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT, headers = {"Content-type=application/json"})
    public Site addSite(@PathVariable String id, @RequestBody @Valid Site site) {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			logger.info("Update site: " + Helper.toJson(site));
			ISiteDao siteDao = session.getMapper(ISiteDao.class);
			siteDao.updateSite(site);
			session.commit();
			return site;
		} finally {
			session.close();
		}
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteSite(@PathVariable Integer id) {
		SqlSession session = DbConnection.getSession().openSession();
		try {
			logger.info("Delete site: " + id);
			ISiteDao siteDao = session.getMapper(ISiteDao.class);
			siteDao.deleteSite(id);
			session.commit();
			return "{\"code\":\"0\"}";
		} finally {
			session.close();
		}
    }
}

