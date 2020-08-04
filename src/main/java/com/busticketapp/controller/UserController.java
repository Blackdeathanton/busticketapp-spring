package com.busticketapp.controller;



import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.busticketapp.event.DashboardEvent;
import com.busticketapp.model.Booking;
import com.busticketapp.model.Bus;
import com.busticketapp.model.User;
import com.busticketapp.service.BookingService;
import com.busticketapp.service.BusService;
import com.busticketapp.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class UserController implements ApplicationEventPublisherAware {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BusService busService;
	
	@Autowired
	private BookingService bookingService;
	
	private ApplicationEventPublisher publisher;
	
	@RequestMapping(value= {"/", "/login"}, method=RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		model.setViewName("user/login");
		return model;
	}
	
	@RequestMapping(value= {"/register"}, method=RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject("user", user);
		model.setViewName("user/register");
		
		return model;
	}
	
	@RequestMapping(value= {"/register"}, method=RequestMethod.POST)
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		
		if(userExists != null) {
			bindingResult.rejectValue("email", "error.user", "This email already exists!");
		}
		if(bindingResult.hasErrors()) {
			model.setViewName("user/register");
		} else {
			userService.saveUser(user);
			model.addObject("message", "User has been successfully registered");
			model.addObject("user", new User());
			model.setViewName("user/login");
		}
		return model;
	}
	
	@RequestMapping(value= {"/dashboard/deleteAccount"}, method=RequestMethod.GET)
	public ModelAndView deleteUser() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		System.out.println(user.getId());
		userService.deleteUser(user.getId());
		model.setViewName("user/login");
		return model;
	}
	
	@RequestMapping(value= {"/dashboard"}, method=RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		model.addObject("userName", user.getFirstname() + " " + user.getLastname());
		model.setViewName("home/dashboard");
		DashboardEvent event = new DashboardEvent(this);
		publisher.publishEvent(event);
		
		return model;
	}
	
	@RequestMapping(value= {"/dashboard/searchBuses"}, method=RequestMethod.GET)
	public ModelAndView searchBuses() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<Bus> buses = busService.findAllBuses();
		model.addObject("buses", buses);
		model.addObject("userName", user.getFirstname() + " " + user.getLastname());
		model.setViewName("home/searchBuses");
		return model;
	}
	
	@RequestMapping(value= {"/dashboard/searchBuses"}, method=RequestMethod.POST)
	public ModelAndView redirectToBooking(@RequestParam(value = "book", required = true) int busid) {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Bus bus = busService.findBusByBusid(busid);
		model.addObject("bus", bus);
		model.addObject("userName", user.getFirstname() + " " + user.getLastname());
		model.addObject("userId", user.getId());
		model.setViewName("home/bookTicket");
		return model;
	}
	
	@RequestMapping(value= {"/dashboard/bookTickets"}, method=RequestMethod.POST)
	public ModelAndView bookTickets(HttpServletRequest request) throws IOException {
		InputStreamReader input = new InputStreamReader(request.getInputStream());
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		Booking booking = gson.fromJson(input, Booking.class);
		bookingService.saveBookings(booking);
		ModelAndView model = new ModelAndView();
		model.setViewName("home/booked");
		return model;
	}
	
	@RequestMapping(value= {"/dashboard/success"}, method=RequestMethod.GET)
	public ModelAndView success() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		model.addObject("userName", user.getFirstname() + " " + user.getLastname());		
		model.setViewName("home/booked");
		return model;
	}
	
	@RequestMapping(value= {"/dashboard/myBookings"}, method=RequestMethod.GET)
	public ModelAndView myBookings() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		List<Booking> bookings= bookingService.getAllBookings(user.getId());
		
		HashMap<Integer, Bus> buses = new HashMap<Integer, Bus>();
		for(Booking booking: bookings) {
			Bus bus = busService.findBusByBusid(booking.getBusid());
			buses.put(booking.getBusid(), bus);
		}
		model.addObject("buses", buses);
		model.addObject("bookings", bookings);
		model.addObject("userName", user.getFirstname() + " " + user.getLastname());
		model.setViewName("home/myBookings");
		return model;
	}
	
	@RequestMapping(value= {"/accessDenied"}, method=RequestMethod.GET) 
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/accessDenied");
		return model; 
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
