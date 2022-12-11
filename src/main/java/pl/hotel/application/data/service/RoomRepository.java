package pl.hotel.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.hotel.application.data.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

}
