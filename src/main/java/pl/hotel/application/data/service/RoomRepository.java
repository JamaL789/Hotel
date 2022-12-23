package pl.hotel.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import pl.hotel.application.data.RoomType;
import pl.hotel.application.data.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	@Query("SELECT r FROM Room r WHERE r.roomType = ?1 AND r.balcony = ?2")
	Room getRoomByTypeAndBalcony(RoomType roomType, boolean isBalcony);
	
	@Query("SELECT r FROM Room r WHERE r.roomType = ?1")
	Room getRoomByType(RoomType roomType);
	/*
	@Modifying
	@Transactional
	@Query("UPDATE Room r SET r.amountFree = ?1, r.amountReserved = ?2 WHERE r.id = ?2")
	void updateRoomCount(int free, int reserved, int id);*/
}
