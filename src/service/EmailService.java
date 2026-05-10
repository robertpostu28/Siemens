package service;

import model.Booking;

public class EmailService {
    public void sendBookingConfirmation(Booking booking) {
        System.out.println("Confirmation email sent to " + booking.getEmail()
                + " for " + booking.getTickets() + " ticket(s) on train "
                + booking.getTrain().getCode() + ".");
    }

    public void sendDelayNotification(Booking booking, int delayMinutes) {
        System.out.println("Delay email sent to " + booking.getEmail()
                + ": train " + booking.getTrain().getCode() + " has a delay of "
                + delayMinutes + " minutes.");
    }
}
