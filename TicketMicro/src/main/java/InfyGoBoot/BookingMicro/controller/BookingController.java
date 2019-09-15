/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InfyGoBoot.BookingMicro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import InfyGoBoot.BookingMicro.Config.BookingRibbonConfig;
import InfyGoBoot.BookingMicro.dto.BookingDetails;
import InfyGoBoot.BookingMicro.dto.PassengerDetails;
import InfyGoBoot.BookingMicro.entity.Flight;
import InfyGoBoot.BookingMicro.entity.Passenger;
import InfyGoBoot.BookingMicro.entity.Ticket;
import InfyGoBoot.BookingMicro.exception.ARSServiceException;
import InfyGoBoot.BookingMicro.exception.ExceptionConstants;
import InfyGoBoot.BookingMicro.exception.InfyGoServiceException;
import InfyGoBoot.BookingMicro.service.FlightService;
import InfyGoBoot.BookingMicro.service.PassengerService;
import InfyGoBoot.BookingMicro.service.TicketService;
import InfyGoBoot.BookingMicro.utility.ClientErrorInformation;


@RestController
@RibbonClient (name="bookingribbon",configuration=BookingRibbonConfig.class)
@RequestMapping("/book")
public class BookingController {

	protected Logger logger = Logger.getLogger(BookingController.class.getName());

	@Autowired
	private FlightService flightService;	
	@Autowired
	private TicketService ticketService;
	@Autowired
	private PassengerService passengerService;
	@Autowired
	RestTemplate template;
	private Ticket ticket;
	private int noOfSeats;
	

	public BookingController() {
		ticket = new Ticket();		
	}


	@PostMapping(value = "/{flightId}/{username}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<BookingDetails> bookFlight(@PathVariable("flightId") String flightId,
		 @Valid @RequestBody PassengerDetails passengerDetails, @PathVariable("username") String username,Errors errors) throws InfyGoServiceException, ARSServiceException {
			
		    if (errors.hasErrors()) {
			return new ResponseEntity(new ClientErrorInformation(HttpStatus.BAD_REQUEST.value(),errors.getFieldError("passengerList").getDefaultMessage()), HttpStatus.BAD_REQUEST);
		    }
		if(passengerDetails.getPassengerList().isEmpty())
        	throw new InfyGoServiceException(ExceptionConstants.PASSENGER_LIST_EMPTY.toString());
        	
		List<Passenger> passengerList = new ArrayList<Passenger>();
		for (Passenger passengers : passengerDetails.getPassengerList()) {
			passengerList.add(passengers);
		    

		}
		System.out.println(passengerList.toString());

		logger.log(Level.INFO, "Book Flight method ");

		logger.log(Level.INFO, passengerDetails.toString());
		int pnr = (int) (Math.random() * 1858955);

		ticket.setPnr(pnr);
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
          
		Flight flight = flightService.getFlights(flightId);

		double fare = flight.getFare();
		System.out.println("Fare per person:****** " + fare);
		System.out.println("List size:****** " + passengerDetails.getPassengerList().size());
		double totalFare = fare * (passengerDetails.getPassengerList().size());

		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setPassengerList(passengerDetails.getPassengerList());
		bookingDetails.setPnr(pnr);
		bookingDetails.setTotalFare(totalFare);
		ticket.setBookingDate(new Date());
		System.out.println(ticket.getBookingDate());
		ticket.setDepartureDate(flight.getFlightAvailableDate());
		ticket.setDepartureTime(flight.getDepartureTime());
		ticket.setFlightId(flight.getFlightId());
		ticket.setUserId(username);		
		ticket.setTotalFare(totalFare);
		noOfSeats = passengerDetails.getPassengerList().size();
		ticket.setNoOfSeats(noOfSeats);
	    ticketService.createTicket(ticket);
    
		addPassengers(bookingDetails.getPassengerList());
		
		flightService.updateFlight(flightId, noOfSeats);

		return new ResponseEntity<BookingDetails>(bookingDetails, HttpStatus.OK);

	}

	private void addPassengers(List<Passenger> passengers) {
		
		for (Passenger passenger : passengers) {
			passenger.setTicket(ticket);	    

		}

		passengerService.createPassenger(passengers);

	}

}
