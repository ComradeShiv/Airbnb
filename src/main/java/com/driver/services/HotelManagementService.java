package com.driver.services;

import com.driver.controllers.repositories.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        if(hotel == null || hotel.getHotelName() == null)
            return "FAILURE";

        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        booking.setBookingId(String.valueOf(randomUUID()));
        return hotelManagementRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagementRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }
}
