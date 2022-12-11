package pl.hotel.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.hotel.application.data.RoomType;

@Entity
@Table(name = "Room")
public class Room {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	
	@Enumerated(EnumType.STRING)
    private RoomType roomType;
	private double price;
	private int amountFree;
	private int amountReserved;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAmountFree() {
		return amountFree;
	}
	public void setAmountFree(int amountFree) {
		this.amountFree = amountFree;
	}
	public int getAmountReserved() {
		return amountReserved;
	}
	public void setAmountReserved(int amountReserved) {
		this.amountReserved = amountReserved;
	}
}
