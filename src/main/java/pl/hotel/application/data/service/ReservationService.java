package pl.hotel.application.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.hotel.application.data.entity.Reservation;

@Service
public class ReservationService {
	
	private final ReservationRepository repository;
	@Autowired
	public ReservationService(ReservationRepository repository) {
	    this.repository = repository;
	}
	public void addReservation(Reservation res) {
		repository.save(res);
	}
	
}
