package pl.hotel.application.data.entity;

import java.io.Serializable;

public class ReservationId implements Serializable{

	private int reservationNumber;
	private int positionNumber;
	
	public ReservationId(int reservationNumber, int positionNumber) {
		this.reservationNumber = reservationNumber;
		this.positionNumber = positionNumber;
	}
	public ReservationId() {}
}
