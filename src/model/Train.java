package model;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private final String code;
    private final List<Station> route;
    private final int totalSeats;
    private final List<Booking> bookings;

    public Train(String code, List<Station> route, int totalSeats) {
        this.code = code;
        this.route = route;
        this.totalSeats = totalSeats;
        this.bookings = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getRouteDescription() {
        List<String> stationNames = new ArrayList<>();
        for (Station station : route) {
            stationNames.add(station.getName());
        }
        return String.join(" -> ", stationNames);
    }

    public int getAvailableSeats() {
        return totalSeats - getBookedSeats();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    private int getBookedSeats() {
        int bookedSeats = 0;
        for (Booking booking : bookings) {
            bookedSeats += booking.getTickets();
        }
        return bookedSeats;
    }
}
