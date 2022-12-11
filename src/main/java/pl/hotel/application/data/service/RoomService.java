package pl.hotel.application.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
	private final RoomRepository repository;
	@Autowired
	public RoomService(RoomRepository repository) {
	    this.repository = repository;
	}
	
	
}
