package com.busticketapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus")
public class Bus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="busid")
	private int busid;
	
	@Column(name="source")
	private String source;
	
	@Column(name="destination")
	private String destination;
	
	@Column(name="travels")
	private String travels;
	
	@Column(name="price")
	private int price;
	
	@Column(name="seats_remaining")
	private int seats_remaining;
	
	@Column(name="total_capacity")
	private int total_capacity;

	public int getBusid() {
		return busid;
	}
	public void setBusid(int busid) {
		this.busid = busid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTravels() {
		return travels;
	}

	public void setTravels(String travels) {
		this.travels = travels;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSeats_remaining() {
		return seats_remaining;
	}

	public void setSeats_remaining(int seats_remaining) {
		this.seats_remaining = seats_remaining;
	}

	public int getTotal_capacity() {
		return total_capacity;
	}

	public void setTotal_capacity(int total_capacity) {
		this.total_capacity = total_capacity;
	}
}
