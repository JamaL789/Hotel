package pl.hotel.application.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.hotel.application.data.RoomType;
import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.Room;

@Service
public class RoomService {
	private final RoomRepository repository;
	@Autowired
	public RoomService(RoomRepository repository) {
	    this.repository = repository;
	}
	
	public Room getRoomByTypeAndBalcony(RoomType roomType, boolean isBalcony) {
		return repository.getRoomByTypeAndBalcony(roomType, isBalcony);
	}
	public Room getRoomByType(RoomType roomType) {
		return repository.getRoomByType(roomType);
	}/*
	public void updateRoomCount(int free, int reserved, int id) {
		repository.updateRoomCount(free, reserved, id);
	}
	*/
	public List<Room> getRooms(){
		return repository.findAll();
	}
	
	public void addReservationToRoom(Room room, Reservation res) {
	//	r.setReservations(null);
		List<Reservation> reservations = new ArrayList<>(room.getReservations());
		reservations.add(res);
		room.setReservations(reservations.stream().collect(Collectors.toSet()));
		repository.save(room);
	}
	public void deleteReservationFromRoom(Room room, Reservation res) {
//		List<Reservation> reservations = new ArrayList<>(room.getReservations());
		Set<Reservation> reservations = room.getReservations();
		reservations.remove(res);
		room.setReservations(reservations);
		repository.save(room);
	}
}
