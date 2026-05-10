package model;

import java.time.LocalTime;

public class JourneyOption {
    private final Train firstTrain;
    private final Train secondTrain;
    private final String departureStation;
    private final String changeStation;
    private final String arrivalStation;
    private final LocalTime departureTime;
    private final LocalTime changeArrivalTime;
    private final LocalTime changeDepartureTime;
    private final LocalTime arrivalTime;

    public JourneyOption(Train train, String departureStation, String arrivalStation,
                         LocalTime departureTime, LocalTime arrivalTime) {
        this.firstTrain = train;
        this.secondTrain = null;
        this.departureStation = departureStation;
        this.changeStation = null;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.changeArrivalTime = null;
        this.changeDepartureTime = null;
        this.arrivalTime = arrivalTime;
    }

    public JourneyOption(Train firstTrain, Train secondTrain, String departureStation, String changeStation,
                         String arrivalStation, LocalTime departureTime, LocalTime changeArrivalTime,
                         LocalTime changeDepartureTime, LocalTime arrivalTime) {
        this.firstTrain = firstTrain;
        this.secondTrain = secondTrain;
        this.departureStation = departureStation;
        this.changeStation = changeStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.changeArrivalTime = changeArrivalTime;
        this.changeDepartureTime = changeDepartureTime;
        this.arrivalTime = arrivalTime;
    }

    public boolean hasChangeover() {
        return secondTrain != null;
    }

    public String getDescription() {
        if (!hasChangeover()) {
            return "Direct: train " + firstTrain.getCode() + " leaves " + departureStation + " at "
                    + departureTime + " and arrives in " + arrivalStation + " at " + arrivalTime + ".";
        }

        return "Changeover: train " + firstTrain.getCode() + " leaves " + departureStation + " at "
                + departureTime + ", arrives in " + changeStation + " at " + changeArrivalTime
                + "; train " + secondTrain.getCode() + " leaves " + changeStation + " at "
                + changeDepartureTime + " and arrives in " + arrivalStation + " at " + arrivalTime + ".";
    }
}
