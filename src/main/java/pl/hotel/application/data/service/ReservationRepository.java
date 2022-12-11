package pl.hotel.application.data.service;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.hotel.application.data.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
