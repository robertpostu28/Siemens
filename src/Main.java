import model.ScheduledStop;
import model.Train;
import model.User;
import repository.TrainRepository;
import repository.UserRepository;
import service.AdminService;
import service.BookingService;
import service.EmailService;
import service.RouteSearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        TrainRepository trainRepository = new TrainRepository();
        UserRepository userRepository = new UserRepository();
        EmailService emailService = new EmailService();
        BookingService bookingService = new BookingService(trainRepository, emailService);
        RouteSearchService routeSearchService = new RouteSearchService(trainRepository);
        AdminService adminService = new AdminService(trainRepository, emailService);

        System.out.println("Train ticketing application");

        while (true) {
            System.out.print("Enter your email or type exit: ");
            String email = scanner.nextLine().trim();
            if (email.equalsIgnoreCase("exit")) {
                break;
            }

            User currentUser = userRepository.findOrCreateByEmail(email);
            if (currentUser.isAdmin()) {
                showAdminMenu(trainRepository, adminService);
            } else {
                showCustomerMenu(currentUser, trainRepository, bookingService, routeSearchService);
            }
        }
    }

    private static void showCustomerMenu(User user, TrainRepository trainRepository,
                                         BookingService bookingService, RouteSearchService routeSearchService) {
        int option;
        do {
            System.out.println();
            System.out.println("Customer menu");
            System.out.println("1. Show available trains");
            System.out.println("2. Book tickets");
            System.out.println("3. Search routes between two stations");
            System.out.println("0. Logout");
            option = readInt("Choose an option: ");

            if (option == 1) {
                showAvailableTrains(trainRepository);
            } else if (option == 2) {
                String trainCode = readText("Train code: ");
                int tickets = readInt("Number of tickets: ");
                bookingService.bookTickets(trainCode, user.getEmail(), user.getEmail(), tickets);
            } else if (option == 3) {
                String departure = readText("Departure station: ");
                String arrival = readText("Arrival station: ");
                routeSearchService.printJourneys(departure, arrival);
            } else if (option != 0) {
                System.out.println("Invalid option.");
            }
        } while (option != 0);
    }

    private static void showAdminMenu(TrainRepository trainRepository, AdminService adminService) {
        int option;
        do {
            System.out.println();
            System.out.println("Admin menu");
            System.out.println("1. Show available trains");
            System.out.println("2. Add train");
            System.out.println("3. Remove train");
            System.out.println("4. Modify train");
            System.out.println("5. Add route to train");
            System.out.println("6. Modify route");
            System.out.println("7. Remove route");
            System.out.println("8. Show bookings for a train");
            System.out.println("9. Report train delay");
            System.out.println("0. Logout");
            option = readInt("Choose an option: ");

            if (option == 1) {
                showAvailableTrains(trainRepository);
            } else if (option == 2) {
                String trainCode = readText("New train code: ");
                int seats = readInt("Number of seats: ");
                adminService.addTrain(trainCode, readRoute(trainRepository), seats);
            } else if (option == 3) {
                adminService.removeTrain(readText("Train code: "));
            } else if (option == 4) {
                String trainCode = readText("Train code: ");
                int seats = readInt("New number of seats: ");
                adminService.modifyTrain(trainCode, readRoute(trainRepository), seats);
            } else if (option == 5) {
                adminService.addRouteToTrain(readText("Train code: "), readRoute(trainRepository));
            } else if (option == 6) {
                adminService.modifyRoute(readText("Train code: "), readRoute(trainRepository));
            } else if (option == 7) {
                adminService.removeRoute(readText("Train code: "));
            } else if (option == 8) {
                adminService.showBookings(readText("Train code: "));
            } else if (option == 9) {
                String trainCode = readText("Train code: ");
                int delayMinutes = readInt("Delay in minutes: ");
                adminService.reportDelay(trainCode, delayMinutes);
            } else if (option != 0) {
                System.out.println("Invalid option.");
            }
        } while (option != 0);
    }

    private static void showAvailableTrains(TrainRepository trainRepository) {
        System.out.println();
        System.out.println("Available trains:");
        for (Train train : trainRepository.findAll()) {
            System.out.println(train.getCode() + " | " + train.getRouteDescription()
                    + " | free seats: " + train.getAvailableSeats());
        }
    }

    private static List<ScheduledStop> readRoute(TrainRepository trainRepository) {
        int stopCount = readInt("Number of stations in route: ");
        List<ScheduledStop> route = new ArrayList<>();

        for (int i = 1; i <= stopCount; i++) {
            System.out.println("Station " + i);
            String stationName = readText("Name: ");
            int hour = readInt("Hour: ");
            int minute = readInt("Minute: ");
            route.add(trainRepository.stop(stationName, hour, minute));
        }

        return route;
    }

    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
