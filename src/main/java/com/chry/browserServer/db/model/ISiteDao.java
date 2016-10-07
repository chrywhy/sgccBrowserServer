package com.chry.browserServer.db.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ISiteDao {
    public Site getSiteById(Integer id);
    public Site getSiteByUrl(String url);
    public List<Site> getAllSites();
    public List<String> getAllTypes();
    public void insertSite(Site site);
    public void deleteSite(Integer id);
    public void updateSite(Site site);
}
