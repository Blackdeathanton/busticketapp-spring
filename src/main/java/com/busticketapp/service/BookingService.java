package com.busticketapp.service;

import java.util.List;

import com.busticketapp.model.Booking;

public interface BookingService {
	
	public void saveBookings(Booking booking);
	public List<Booking> getAllBookings(int userid);
}
