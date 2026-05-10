package model;

public class Booking {
    private final String customerName;
    private final String email;
    private final int tickets;
    private final Train train;

    public Booking(String customerName, String email, int tickets, Train train) {
        this.customerName = customerName;
        this.email = email;
        this.tickets = tickets;
        this.train = train;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public int getTickets() {
        return tickets;
    }

    public Train getTrain() {
        return train;
    }
}
