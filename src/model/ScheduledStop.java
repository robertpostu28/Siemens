package model;

import java.time.LocalTime;

public class ScheduledStop {
    private final Station station;
    private final LocalTime time;

    public ScheduledStop(Station station, LocalTime time) {
        this.station = station;
        this.time = time;
    }

    public Station getStation() {
        return station;
    }

    public LocalTime getTime() {
        return time;
    }
}
