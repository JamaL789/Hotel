package pl.hotel.application.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.hotel.application.data.RoomType;
import pl.hotel.application.data.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	// zwrot pokoju na podstawie rodzaju i balkonu
	@Query("SELECT r FROM Room r WHERE r.roomType = ?1 AND r.balcony = ?2")
	List<Room> getRoomByTypeAndBalcony(RoomType roomType, boolean isBalcony);
	// zwrot pokoju na podstawie rodzaju
	@Query("SELECT r FROM Room r WHERE r.roomType = ?1")
	List<Room> getRoomByType(RoomType roomType);
}
