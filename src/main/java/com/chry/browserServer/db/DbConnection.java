package com.chry.browserServer.db;

import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chry.browserServer.Application;

public class DbConnection {
	static Logger logger = LogManager.getLogger(DbConnection.class.getName());
	private static SqlSessionFactory _sqlSessionFactory;
	private static Reader _reader;

	public static void init(String host, String user, String password) {
		try {
			Properties properties = new Properties();
			properties.setProperty("jdbc.host", host);
			properties.setProperty("jdbc.user", user);
			properties.setProperty("jdbc.password", password);
			
			_reader = Resources.getResourceAsReader("com/chry/browserServer/db/dbConfig.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();  
			_sqlSessionFactory = builder.build(_reader, properties);  
//			_sqlSessionFactory = new SqlSessionFactoryBuilder().build(_reader, properties);
			if(_sqlSessionFactory != null) {
				logger.info("mysql is connected");
			} else {
				logger.error("mysql connection failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static SqlSessionFactory getSession() {
		return _sqlSessionFactory;
	}
}
