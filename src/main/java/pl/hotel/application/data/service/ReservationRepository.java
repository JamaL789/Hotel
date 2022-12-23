package pl.hotel.application.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.hotel.application.data.entity.Reservation;
import pl.hotel.application.data.entity.Room;
import pl.hotel.application.data.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	@Query("SELECT r FROM Reservation r WHERE r.roomUser = ?1")
	List<Reservation> findReservationsByUser(User user);
	
	@Query("SELECT r FROM Reservation r WHERE r.room = ?1")
	List<Reservation> findReservationsByRoom(Room room);
}
