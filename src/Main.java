import repository.TrainRepository;
import service.BookingService;
import service.EmailService;
import model.Train;
import service.RouteSearchService;

public class Main {
    public static void main(String[] args) {
        TrainRepository trainRepository = new TrainRepository();
        EmailService emailService = new EmailService();
        BookingService bookingService = new BookingService(trainRepository, emailService);
        RouteSearchService routeSearchService = new RouteSearchService(trainRepository);

        System.out.println("Available trains:");
        for (Train train : trainRepository.findAll()) {
            System.out.println(train.getCode() + " | " + train.getRouteDescription()
                    + " | free seats: " + train.getAvailableSeats());
        }

        System.out.println();
        bookingService.bookTickets("IR102", "Ana Popescu", "ana.popescu@mail.com", 2);
        bookingService.bookTickets("IR102", "Mihai Ionescu", "mihai.ionescu@mail.com", 3);
        bookingService.bookTickets("IR102", "Extra Passenger", "extra.passenger@mail.com", 1);
        bookingService.bookTickets("R205", "Elena Pavel", "elena.pavel@mail.com", 1);

        routeSearchService.printJourneys("Bucharest", "Cluj-Napoca");
        routeSearchService.printJourneys("Bucharest", "Targu Mures");
        routeSearchService.printJourneys("Iasi", "Oradea");
    }
}
