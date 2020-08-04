package com.busticketapp.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busticketapp.model.Booking;

@Repository("bookingRepository")
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	Booking findByBookingid(int id);
	List<Booking> findByUserid(int userid);
}
