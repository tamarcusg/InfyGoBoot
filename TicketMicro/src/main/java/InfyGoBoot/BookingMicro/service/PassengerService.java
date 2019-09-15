package InfyGoBoot.BookingMicro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import InfyGoBoot.BookingMicro.dto.PassengerDetails;
import InfyGoBoot.BookingMicro.entity.Passenger;
import InfyGoBoot.BookingMicro.repository.PassengerRepository;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository passengerRepository;

	public void createPassenger(List<Passenger> passengers) {

		passengerRepository.saveAll(passengers);

	}
	
}
