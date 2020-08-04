package com.busticketapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.busticketapp.model.Booking;

public class BookingDAO {
	
	@Autowired
	DAObject connObject = new DAObject();
	
	public ArrayList<Booking> fetchAllBuses(int userid) throws Exception {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		try {
			Connection connection = connObject.createConn();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM bus;");
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				Booking booking = new Booking();
				booking.setBookingid(result.getInt("bookingid"));
				booking.setUserid(result.getInt("userid"));
				booking.setBusid(result.getInt("busid"));
				booking.setTotalprice(result.getInt("totalprice"));
				booking.setPassengers(result.getString("passengers"));
				booking.setQuantity(result.getInt("quantity"));
				bookings.add(booking);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return bookings;
	}
}
