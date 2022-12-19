package pl.hotel.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.Room;
import pl.hotel.application.data.service.ReservationService;
import pl.hotel.application.data.service.RoomService;

@RestController
public class HotelController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private ReservationService reservationService;
	
	@GetMapping("/rooms")
	List<Room> getRooms() {
		List<Room> list = roomService.getRooms();
		return list;
	}
	
	@GetMapping("/reservations")
	List<Reservation> getReservations() {
		List<Reservation> list = reservationService.getReservations();
		return list;
	}
	
	@PostMapping("/reservations")
	void newReservation(@RequestBody Reservation newReservation) {
		reservationService.addReservation(newReservation);
	}
}
