package com.busticketapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busticketapp.model.Bus;
import com.busticketapp.repository.BusRepository;

@Service("busService")
public class BusServiceImpl implements BusService {
	
	@Autowired
	private BusRepository busRepository;
	
	@Override
	public Bus findBusByBusid(int busid) {
		return busRepository.findBusByBusid(busid);
	}

	@Override
	public List<Bus> findAllBuses() {
		return busRepository.findAll();
	}
}
