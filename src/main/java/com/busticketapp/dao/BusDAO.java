package com.busticketapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import com.busticketapp.model.Bus;

public class BusDAO {
	
	@Autowired
	DAObject connObject = new DAObject();
	
	public ArrayList<Bus> fetchAllBuses() throws Exception {
		ArrayList<Bus> buses = new ArrayList<Bus>();
		try {
			Connection connection = connObject.createConn();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM bus;");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Bus newBus = new Bus();
				newBus.setBusid(result.getInt("busid"));
				newBus.setSource(result.getString("source"));
				newBus.setDestination(result.getString("destination"));
				newBus.setPrice(result.getInt("price"));
				newBus.setTravels(result.getString("travels"));
				newBus.setSeats_remaining(result.getInt("seats_remaining"));
				newBus.setTotal_capacity(result.getInt("total_capacity"));
				buses.add(newBus);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return buses;
	}
}
