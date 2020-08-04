package com.busticketapp.service;

import java.util.List;

import com.busticketapp.model.Bus;

public interface BusService {
	
	public Bus findBusByBusid(int busid);
	public List<Bus> findAllBuses();
}
