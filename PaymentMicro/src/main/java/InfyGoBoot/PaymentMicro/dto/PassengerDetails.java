package InfyGoBoot.PaymentMicro.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import InfyGoBoot.PaymentMicro.entity.Passenger;

public class PassengerDetails {
	@NotEmpty(message = "Passenger List cannot be empty for booking!")
	List<Passenger> passengerList;

	public List<Passenger> getPassengerList() {
		return passengerList;
	}

	public PassengerDetails() {
		super();
	}

	public PassengerDetails(List<Passenger> passengerList) {
		super();
		this.passengerList = passengerList;
	}

	public void setPassengerList(List<Passenger> passengerList) {
		this.passengerList = passengerList;
	}

	@Override
	public String toString() {
		return "PassengerDetails [passengerList=" + passengerList + "]";
	}

}
