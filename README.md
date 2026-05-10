# Train Ticketing Application

This is a simple Java console application for managing train routes, schedules and ticket bookings.

The application supports three types of functionality:

- customers can book one or more train tickets;
- customers can search for possible routes between two stations;
- administrators can manage trains, routes, bookings and train delays.

Email sending is simulated in the console. The application prints confirmation and delay messages instead of sending real emails through SMTP.

## Project Structure

```text
src/
  Main.java
  model/
    Booking.java
    JourneyOption.java
    ScheduledStop.java
    Station.java
    Train.java
    User.java
  repository/
    TrainRepository.java
    UserRepository.java
  service/
    AdminService.java
    BookingService.java
    EmailService.java
    RouteSearchService.java
```

## Login Flow

The application starts by asking for an email address.

The email is checked in `UserRepository`.

- `admin@railway.com` is stored as an administrator.
- Other emails are treated as customers.

There is no password. The role is decided by the stored `isAdmin` value.

## Customer Functionalities

### 1. Show Available Trains

Example input:

```text
Enter your email or type exit: ana.popescu@mail.com
1
```

Example output:

```text
Available trains:
IR102 | Bucharest -> Brasov -> Sibiu -> Cluj-Napoca | free seats: 5
R205 | Timisoara -> Arad -> Oradea | free seats: 3
RE310 | Iasi -> Bacau -> Ploiesti -> Bucharest | free seats: 4
IC401 | Brasov -> Sighisoara -> Targu Mures | free seats: 6
IR550 | Oradea -> Cluj-Napoca -> Brasov | free seats: 4
```

### 2. Book Tickets

The booking system checks the existing bookings for the train. If there are not enough free seats, the booking is rejected.

Example input:

```text
2
IR102
2
```

Example output:

```text
Confirmation email sent to ana.popescu@mail.com for 2 ticket(s) on train IR102.
Booking completed for ana.popescu@mail.com: 2 ticket(s) on train IR102.
```

Overbooking example:

```text
Booking failed for customer@mail.com: only 0 seats are still available on train IR102.
```

### 3. Search Routes Between Stations

The application can find direct routes and routes that require one train change.

Example input:

```text
3
Bucharest
Cluj-Napoca
```

Example output:

```text
Routes from Bucharest to Cluj-Napoca:
Direct: train IR102 leaves Bucharest at 08:00 and arrives in Cluj-Napoca at 15:45.
```

Changeover example:

```text
Routes from Bucharest to Targu Mures:
Changeover: train IR102 leaves Bucharest at 08:00, arrives in Brasov at 10:30; train IC401 leaves Brasov at 11:00 and arrives in Targu Mures at 14:00.
```

No connection example:

```text
Routes from Iasi to Oradea:
No possible connection was found between these stations.
```

## Administrator Functionalities

Use this email to access the administrator menu:

```text
admin@railway.com
```

The administrator menu supports:

- showing available trains;
- adding trains;
- removing trains;
- modifying trains;
- adding routes to trains;
- modifying routes;
- removing routes;
- showing bookings for a train;
- reporting delays and notifying customers.

### Show Bookings for a Train

Example input:

```text
8
IR102
```

Example output:

```text
Bookings for train IR102:
ana.popescu@mail.com | ana.popescu@mail.com | tickets: 2
```

### Report a Delay

Example input:

```text
9
IR102
20
```

Example output:

```text
Train IR102 was marked with a delay of 20 minutes.
Delay email sent to ana.popescu@mail.com: train IR102 has a delay of 20 minutes.
```

### Add a Train

Example input:

```text
2
IR777
8
3
Constanta
9
15
Bucharest
11
45
Brasov
14
10
```

Example output:

```text
Train IR777 was added.
```

### Modify a Route

Example input:

```text
6
IR777
4
Constanta
9
0
Bucharest
11
30
Brasov
13
55
Sibiu
16
20
```

Example output:

```text
Route was modified for train IR777.
```

### Remove a Train

Example input:

```text
3
IR777
```

Example output:

```text
Train IR777 was removed.
```

## Notes

All data is stored in memory while the program is running. When the application stops, newly added trains, modified routes and bookings are lost.

The email feature is a demo implementation. It prints messages to the console, which keeps the project simple and free to run.
