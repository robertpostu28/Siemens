package repository;

import model.Station;
import model.Train;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrainRepository {
    private final List<Train> trains;

    public TrainRepository() {
        trains = Arrays.asList(
                new Train("IR102", Arrays.asList(
                        new Station("Bucharest"),
                        new Station("Brasov"),
                        new Station("Sibiu"),
                        new Station("Cluj-Napoca")
                ), 5),
                new Train("R205", Arrays.asList(
                        new Station("Timisoara"),
                        new Station("Arad"),
                        new Station("Oradea")
                ), 3),
                new Train("RE310", Arrays.asList(
                        new Station("Iasi"),
                        new Station("Bacau"),
                        new Station("Ploiesti"),
                        new Station("Bucharest")
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
}
