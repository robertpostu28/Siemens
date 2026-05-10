package repository;

import model.ScheduledStop;
import model.Station;
import model.Train;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrainRepository {
    private final List<Train> trains;

    public TrainRepository() {
        trains = Arrays.asList(
                new Train("IR102", Arrays.asList(
                        stop("Bucharest", 8, 0),
                        stop("Brasov", 10, 30),
                        stop("Sibiu", 12, 15),
                        stop("Cluj-Napoca", 15, 45)
                ), 5),
                new Train("R205", Arrays.asList(
                        stop("Timisoara", 7, 20),
                        stop("Arad", 8, 30),
                        stop("Oradea", 10, 5)
                ), 3),
                new Train("RE310", Arrays.asList(
                        stop("Iasi", 6, 45),
                        stop("Bacau", 8, 0),
                        stop("Ploiesti", 11, 30),
                        stop("Bucharest", 12, 25)
                ), 4),
                new Train("IC401", Arrays.asList(
                        stop("Brasov", 11, 0),
                        stop("Sighisoara", 12, 25),
                        stop("Targu Mures", 14, 0)
                ), 6),
                new Train("IR550", Arrays.asList(
                        stop("Oradea", 10, 40),
                        stop("Cluj-Napoca", 13, 10),
                        stop("Brasov", 17, 50)
                ), 4)
        );
    }

    public List<Train> findAll() {
        return Collections.unmodifiableList(trains);
    }

    public Train findByCode(String code) {
        for (Train train : trains) {
            if (train.getCode().equalsIgnoreCase(code)) {
                return train;
            }
        }
        return null;
    }

    private ScheduledStop stop(String stationName, int hour, int minute) {
        return new ScheduledStop(new Station(stationName), LocalTime.of(hour, minute));
    }
}
