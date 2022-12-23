package pl.hotel.application.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.Room;
import pl.hotel.application.data.entity.User;

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
	public void removeReservation(Reservation res) {
		repository.delete(res);
	}
	public List<Reservation> getReservationsByUser(User user){
		return repository.findReservationsByUser(user);
	}
	public List<Reservation> getReservationsByRoom(Room room){
		return repository.findReservationsByRoom(room);
	}
	public List<Reservation> getReservations(){
		return repository.findAll();
	}
}
