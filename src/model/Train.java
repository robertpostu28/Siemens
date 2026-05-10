package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Train {
    private final String code;
    private final List<ScheduledStop> route;
    private final int totalSeats;
    private final List<Booking> bookings;

    public Train(String code, List<ScheduledStop> route, int totalSeats) {
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
        for (ScheduledStop stop : route) {
            stationNames.add(stop.getStation().getName());
        }
        return String.join(" -> ", stationNames);
    }

    public List<ScheduledStop> getRoute() {
        return Collections.unmodifiableList(route);
    }

    public ScheduledStop getStop(String stationName) {
        for (ScheduledStop stop : route) {
            if (stop.getStation().getName().equalsIgnoreCase(stationName)) {
                return stop;
            }
        }
        return null;
    }

    public boolean travelsFromTo(String departureStation, String arrivalStation) {
        int departureIndex = getStationIndex(departureStation);
        int arrivalIndex = getStationIndex(arrivalStation);
        return departureIndex != -1 && arrivalIndex != -1 && departureIndex < arrivalIndex;
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

    private int getStationIndex(String stationName) {
        for (int i = 0; i < route.size(); i++) {
            ScheduledStop stop = route.get(i);
            if (stop.getStation().getName().equalsIgnoreCase(stationName)) {
                return i;
            }
        }
        return -1;
    }
}
