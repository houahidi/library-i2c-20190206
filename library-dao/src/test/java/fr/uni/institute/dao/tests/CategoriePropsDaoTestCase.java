package fr.uni.institute.dao.tests;



import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import fr.uni.institute.library.business.inventory.Category;
import fr.uni.institute.library.dao.CategoryDao;
import fr.uni.institute.library.dao.DaoException;
import fr.uni.institute.library.dao.jdbc.CategoryDaoJdbc;

public class CategoriePropsDaoTestCase {
	
	private static Logger logger = Logger.getLogger(CategoriePropsDaoTestCase.class);
	private CategoryDao categorieDao;
	private int nombreCategorie ;
	@Before
	public void setUp() throws Exception {
		logger.info("Initialisation des ressources");
		
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "db.properties";	
		 
		Properties appProps = new Properties();
		appProps.load(new FileInputStream(appConfigPath));
		 
		
		 
		String driver = appProps.getProperty("driver"); 
		String url =  appProps.getProperty("url"); 
		String login = appProps.getProperty("login"); 
		String password =  appProps.getProperty("password"); 
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, login,	password);		
		
		
		categorieDao = new  CategoryDaoJdbc(connection);
		nombreCategorie = 9;
		
		 
		
	}

	@After
	public void tearDown() throws Exception {
		logger.info("Libération des ressources");
	}

	@Test
	public void test1ResearchAllCategories() {
		logger.info("test1ResearchAllCategories");
		
		try {
			Collection<Category> liste = categorieDao.researchAllCategories();
			Assert.assertNotNull(liste);
			Assert.assertEquals(nombreCategorie, liste.size());
		} catch (DaoException e) {
			Assert.fail(e.getMessage()); 
		}
		
	}



}