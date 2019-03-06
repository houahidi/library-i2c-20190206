package fr.uni.institute.library.web;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import fr.uni.institute.library.dao.jdbc.CategoryDaoJdbc;
import fr.uni.institute.library.service.InventoryManagementService;
import fr.uni.institute.library.service.impl.InventoryManagementServiceImpl;

public class ServletListCategories extends ServletInventory {
	
	private static final  Logger logger = Logger.getLogger(ServletListCategories.class);
	private Connection connection = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		try {
			logger.info("Servlet gestion des catégories");			
			logger.debug("Recuperation d'une connexion");
			connection = getConnection();
			InventoryManagementService service = new InventoryManagementServiceImpl();
			service.setCategoryDao(new CategoryDaoJdbc(connection));
			logger.debug("Recuperation des catégories de la bd");
			Collection categories = service.getAllCategories() ;
			request.setAttribute("categories", categories);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/displaycategories.jsp");
				rd.forward(request, response);
			logger.debug("redirection vers la page jsp :  jsp/displaycategories.jsp");
		} catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace() ;
		}finally{
		
			try {
				if (connection!= null && ! connection.isClosed())
					connection.close();
			
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}	
		}
	}
}
