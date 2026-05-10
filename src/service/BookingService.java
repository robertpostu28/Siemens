package service;

import model.Booking;
import model.Train;
import repository.TrainRepository;

public class BookingService {
    private final TrainRepository trainRepository;
    private final EmailService emailService;

    public BookingService(TrainRepository trainRepository, EmailService emailService) {
        this.trainRepository = trainRepository;
        this.emailService = emailService;
    }

    public boolean bookTickets(String trainCode, String customerName, String email, int tickets) {
        Train train = trainRepository.findByCode(trainCode);

        if (train == null) {
            System.out.println("Booking failed: train " + trainCode + " does not exist.");
            return false;
        }

        if (tickets <= 0) {
            System.out.println("Booking failed: ticket number must be positive.");
            return false;
        }

        if (tickets > train.getAvailableSeats()) {
            System.out.println("Booking failed for " + customerName + ": only "
                    + train.getAvailableSeats() + " seats are still available on train " + trainCode + ".");
            return false;
        }

        Booking booking = new Booking(customerName, email, tickets, train);
        train.addBooking(booking);
        emailService.sendBookingConfirmation(booking);

        System.out.println("Booking completed for " + customerName + ": " + tickets
                + " ticket(s) on train " + trainCode + ".");
        return true;
    }
}
