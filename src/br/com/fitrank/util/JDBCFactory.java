package br.com.fitrank.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCFactory {

	// private static EntityManagerFactory entityManagerFactory=
	// Persistence.createEntityManagerFactory("fitrank");

	// public EntityManager getEntityManager(){

	// return entityManagerFactory.createEntityManager();

	// }

	public Connection getConnection() {
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			
			input = getClass().getClassLoader().getResourceAsStream("config.properties");
			
			// load a properties file
			prop.load(input);
			
			ConstantesFitRank.app_secret = prop.getProperty("app_secret");
			
			return DriverManager.getConnection(
					prop.getProperty("dbconnection"), prop.getProperty("dbuser"), prop.getProperty("dbpassword") );
			
//			return DriverManager.getConnection(
//					"jdbc:mysql://localhost:3306/fitrank", prop.getProperty("dbuser"), prop.getProperty("dbpassword") );
			
//			return DriverManager.getConnection(
//					"jdbc:mysql://10.1.1.186:8101/fitrank", prop.getProperty("dbuser"), prop.getProperty("dbpassword") );

			//Banco local, localhost
//			return DriverManager.getConnection(
//					"jdbc:mysql://localhost:3306/fitrank", "root", "");
			
		} catch (SQLException | ClassNotFoundException | IOException e) {

			throw new RuntimeException(e);
		}
	}

}
