package pl.hotel.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.hotel.application.data.RoomType;
import pl.hotel.application.data.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("SELECT r FROM Room r WHERE r.roomType = ?1 AND r.balcony = ?2")
	Room getRoomByType(RoomType roomType, boolean isBalcony);
}
