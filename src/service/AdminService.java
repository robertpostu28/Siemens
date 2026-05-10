package service;

import model.Booking;
import model.ScheduledStop;
import model.Train;
import repository.TrainRepository;

import java.util.List;

public class AdminService {
    private final TrainRepository trainRepository;
    private final EmailService emailService;

    public AdminService(TrainRepository trainRepository, EmailService emailService) {
        this.trainRepository = trainRepository;
        this.emailService = emailService;
    }

    public void addTrain(String code, List<ScheduledStop> route, int seats) {
        if (trainRepository.addTrain(new Train(code, route, seats))) {
            System.out.println("Train " + code + " was added.");
        } else {
            System.out.println("Train " + code + " already exists.");
        }
    }

    public void removeTrain(String code) {
        if (trainRepository.removeTrain(code)) {
            System.out.println("Train " + code + " was removed.");
        } else {
            System.out.println("Train " + code + " was not found.");
        }
    }

    public void modifyTrain(String code, List<ScheduledStop> newRoute, int newSeatNumber) {
        Train train = trainRepository.findByCode(code);
        if (train == null) {
            System.out.println("Train " + code + " was not found.");
            return;
        }

        train.setRoute(newRoute);
        train.setTotalSeats(newSeatNumber);
        System.out.println("Train " + code + " was modified.");
    }

    public void addRouteToTrain(String trainCode, List<ScheduledStop> route) {
        Train train = trainRepository.findByCode(trainCode);
        if (train == null) {
            System.out.println("Train " + trainCode + " was not found.");
            return;
        }

        train.setRoute(route);
        System.out.println("Route was added to train " + trainCode + ".");
    }

    public void modifyRoute(String trainCode, List<ScheduledStop> route) {
        Train train = trainRepository.findByCode(trainCode);
        if (train == null) {
            System.out.println("Train " + trainCode + " was not found.");
            return;
        }

        train.setRoute(route);
        System.out.println("Route was modified for train " + trainCode + ".");
    }

    public void removeRoute(String trainCode) {
        Train train = trainRepository.findByCode(trainCode);
        if (train == null) {
            System.out.println("Train " + trainCode + " was not found.");
            return;
        }

        train.setRoute(List.of());
        System.out.println("Route was removed from train " + trainCode + ".");
    }

    public void showBookings(String trainCode) {
        Train train = trainRepository.findByCode(trainCode);
        if (train == null) {
            System.out.println("Train " + trainCode + " was not found.");
            return;
        }

        System.out.println();
        System.out.println("Bookings for train " + trainCode + ":");
        if (train.getBookings().isEmpty()) {
            System.out.println("There are no bookings for this train.");
            return;
        }

        for (Booking booking : train.getBookings()) {
            System.out.println(booking.getCustomerName() + " | " + booking.getEmail()
                    + " | tickets: " + booking.getTickets());
        }
    }

    public void reportDelay(String trainCode, int delayMinutes) {
        Train train = trainRepository.findByCode(trainCode);
        if (train == null) {
            System.out.println("Train " + trainCode + " was not found.");
            return;
        }

        train.setDelayMinutes(delayMinutes);
        System.out.println("Train " + trainCode + " was marked with a delay of "
                + delayMinutes + " minutes.");

        for (Booking booking : train.getBookings()) {
            emailService.sendDelayNotification(booking, delayMinutes);
        }
    }
}
