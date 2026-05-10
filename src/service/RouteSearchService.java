package service;

import model.JourneyOption;
import model.ScheduledStop;
import model.Train;
import repository.TrainRepository;

import java.util.ArrayList;
import java.util.List;

public class RouteSearchService {
    private final TrainRepository trainRepository;

    public RouteSearchService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<JourneyOption> findJourneys(String departureStation, String arrivalStation) {
        List<JourneyOption> journeys = new ArrayList<>();

        for (Train train : trainRepository.findAll()) {
            if (train.travelsFromTo(departureStation, arrivalStation)) {
                ScheduledStop departure = train.getStop(departureStation);
                ScheduledStop arrival = train.getStop(arrivalStation);
                journeys.add(new JourneyOption(train, departureStation, arrivalStation,
                        departure.getTime(), arrival.getTime()));
            }
        }

        for (Train firstTrain : trainRepository.findAll()) {
            if (firstTrain.getStop(departureStation) == null) {
                continue;
            }

            for (Train secondTrain : trainRepository.findAll()) {
                if (firstTrain == secondTrain || secondTrain.getStop(arrivalStation) == null) {
                    continue;
                }

                addChangeoverJourneys(journeys, firstTrain, secondTrain, departureStation, arrivalStation);
            }
        }

        return journeys;
    }

    public void printJourneys(String departureStation, String arrivalStation) {
        List<JourneyOption> journeys = findJourneys(departureStation, arrivalStation);

        System.out.println();
        System.out.println("Routes from " + departureStation + " to " + arrivalStation + ":");
        if (journeys.isEmpty()) {
            System.out.println("No possible connection was found between these stations.");
            return;
        }

        for (JourneyOption journey : journeys) {
            System.out.println(journey.getDescription());
        }
    }

    private void addChangeoverJourneys(List<JourneyOption> journeys, Train firstTrain, Train secondTrain,
                                       String departureStation, String arrivalStation) {
        for (ScheduledStop stop : firstTrain.getRoute()) {
            String changeStation = stop.getStation().getName();
            addJourneyIfChangeoverWorks(journeys, firstTrain, secondTrain,
                    departureStation, changeStation, arrivalStation);
        }
    }

    private void addJourneyIfChangeoverWorks(List<JourneyOption> journeys, Train firstTrain, Train secondTrain,
                                             String departureStation, String changeStation, String arrivalStation) {
        if (!firstTrain.travelsFromTo(departureStation, changeStation)
                || !secondTrain.travelsFromTo(changeStation, arrivalStation)) {
            return;
        }

        ScheduledStop departure = firstTrain.getStop(departureStation);
        ScheduledStop changeArrival = firstTrain.getStop(changeStation);
        ScheduledStop changeDeparture = secondTrain.getStop(changeStation);
        ScheduledStop arrival = secondTrain.getStop(arrivalStation);

        if (changeArrival.getTime().isAfter(changeDeparture.getTime())) {
            return;
        }

        journeys.add(new JourneyOption(firstTrain, secondTrain, departureStation, changeStation,
                arrivalStation, departure.getTime(), changeArrival.getTime(),
                changeDeparture.getTime(), arrival.getTime()));
    }
}
