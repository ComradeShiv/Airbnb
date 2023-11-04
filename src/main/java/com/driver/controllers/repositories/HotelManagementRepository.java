package com.driver.controllers.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.*;

public class HotelManagementRepository {

    HashMap<String, Hotel> hotels = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();
    TreeSet<String> lexicographicallyHotelName = new TreeSet<>();
    HashMap<String, Booking> bookings = new HashMap<>();
    HashMap<User, List<Booking>> bookingsPerPerson = new HashMap<>();

    public String addHotel(Hotel hotel) {
        if(hotels.containsKey(hotel.getHotelName()))
            return "FAILURE";

        hotels.put(hotel.getHotelName(), hotel);
        lexicographicallyHotelName.add(hotel.getHotelName());
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        if(users.containsKey(user.getaadharCardNo()))
            return user.getaadharCardNo();

        users.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String maxFacilityHotelName = "";
        int count = 0;
        for(String hotelName: lexicographicallyHotelName) {
            if(hotels.get(hotelName).getFacilities().size() > count) {
                count = hotels.get(hotelName).getFacilities().size();
                maxFacilityHotelName = hotelName;
            }
        }

        if(count == 0)
            return "";

        return maxFacilityHotelName;
    }

    public int bookARoom(Booking booking) {
        if(!hotels.containsKey(booking.getHotelName()) || !users.containsKey(booking.getBookingAadharCard()))
            return -1;

        int bookingsTillNow = 0;
        for(Map.Entry<String, Booking> b: bookings.entrySet()) {
            if(b.getValue().getHotelName() == booking.getHotelName()) {
                bookingsTillNow += b.getValue().getNoOfRooms();
            }
        }
        if(hotels.get(booking.getHotelName()).getAvailableRooms() < bookingsTillNow + booking.getNoOfRooms())
            return -1;

        int pricePerRoom = hotels.get(booking.getHotelName()).getPricePerNight();
        int amountToBePaid = booking.getNoOfRooms() * pricePerRoom;
        booking.setAmountToBePaid(amountToBePaid);

        if(!bookingsPerPerson.containsKey(users.get(booking.getBookingAadharCard())))
            bookingsPerPerson.put(users.get(booking.getBookingAadharCard()), new ArrayList<Booking>());
        bookingsPerPerson.get(users.get(booking.getBookingAadharCard())).add(booking);
        bookings.put(booking.getBookingId(), booking);

        return booking.getAmountToBePaid();
    }

    public int getBookings(Integer aadharCard) {
        if(!users.containsKey(aadharCard))
            return -1;

        return bookingsPerPerson.get(users.containsKey(aadharCard)).size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        for(Facility newVal: newFacilities) {
            boolean isPresent = false;
            for(Facility val: hotels.get(hotelName).getFacilities())
                if(newVal == val)
                    isPresent = true;

            if(isPresent == false)
                hotels.get(hotelName).getFacilities().add(newVal);
        }
        return hotels.get(hotelName);
    }
}
