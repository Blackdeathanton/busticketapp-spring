package com.busticketapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAObject {
	    
	public Connection createConn() throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking","root","password");
	    return connection;
	}
}
