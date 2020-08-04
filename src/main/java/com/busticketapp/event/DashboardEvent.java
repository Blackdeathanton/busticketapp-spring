package com.busticketapp.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class DashboardEvent extends ApplicationEvent {

	public DashboardEvent(Object source) {
		super(source);
	}
	
	public String toString() {
		return "Dashboard has been initialized";
	}
}
