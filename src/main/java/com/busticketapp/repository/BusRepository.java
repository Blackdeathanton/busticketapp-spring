package com.busticketapp.repository;

import java.util.List;

//import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busticketapp.model.Bus;

@Repository("busRepository")
public interface BusRepository extends JpaRepository<Bus, Long> {
	
	Bus findBusByBusid(int busid);
	List<Bus> findAll();
}
