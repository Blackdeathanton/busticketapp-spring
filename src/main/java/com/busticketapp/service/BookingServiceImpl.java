package com.busticketapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busticketapp.model.Booking;
import com.busticketapp.repository.BookingRepository;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;
		
	@Override
	public void saveBookings(Booking booking) {
		bookingRepository.save(booking);
	}

	@Override
	public List<Booking> getAllBookings(int userid) {
		return bookingRepository.findByUserid(userid);
	}
	
	
}
