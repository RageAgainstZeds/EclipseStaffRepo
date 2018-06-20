package main;

import java.util.Properties;

import javax.net.ssl.SSLHandshakeException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class DBConnection {
	
	private static final String DB_PASSWORD = "dbPassword";
	private static final String DB_LOGIN = "dbLogin";
	private static final String DB_URL = "dbURL";
	private static final String DB_PROPERTIES = "C:\\Users\\yaroslav.bilko\\Documents\\RedBarn\\properties\\dbProperties_GenHiveScr.properties";
	private static Properties globalProp;
	
	private static Connection oraCon;
	private static Statement querryState;
	
	private static String dbURL;
	private static String dbLogin;
	private static String dbPassword;
    
	public static Connection getOraCon() {
		return oraCon;
	}

	public static void setOraCon(Connection oraCon) {
		DBConnection.oraCon = oraCon;
	}

	public static String getDbURL() {
		return dbURL;
	}

	public static void setDbURL(String dbURL) {
		dbURL = dbURL;
	}

	public static String getDbLogin() {
		return dbLogin;
	}

	public static void setDbLogin(String dbLogin) {
		dbLogin = dbLogin;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		dbPassword = dbPassword;
	}

	public static Statement getQuerryState() {
		return querryState;
	}

	public static void setQuerryState(Statement querryState) {
		DBConnection.querryState = querryState;
	}
	
	public static void readProperties() {
		
		System.out.print(" -> Read property file...");
		InputStream in;
		try {
			System.out.print("open stream...");
			in = new FileInputStream(DB_PROPERTIES);
			System.out.println(" stream successful.");
			
			System.out.print(" -> Create properties...");
			globalProp = new Properties();
			globalProp.load(in);
			System.out.println("successful.");
			System.out.println(" -> Property file was read succeess!");
			
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void openConnction() throws ClassNotFoundException, SQLException, IOException, NullPointerException { 
		//jdbc connection
		System.out.println("Reading properties: ");
		readProperties();
		System.setProperty("oracle.net.tns_admin", "C:\\Oracle\\product\\11.2.0\\client_1\\network\\admin");
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    System.out.println(" successful.");
	    
	    System.out.print("Opening connection...");
	    oraCon = DriverManager.getConnection(globalProp.getProperty(DB_URL), globalProp.getProperty(DB_LOGIN), globalProp.getProperty(DB_PASSWORD));
	    querryState = oraCon.createStatement();
	    System.out.println(" successful.");
	}
	
	public static void closeConnection() throws SQLException {
		System.out.print("Connection and querry statement closing...");
		querryState.close();
		oraCon.close();
		System.out.println(" closed.");
	}
	
	


    
    
}
