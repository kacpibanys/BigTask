package com.bigtask;

import com.bigtask.Booking.Booking;
import com.bigtask.Booking.BookingService;
import com.bigtask.Invoice.Billable;
import com.bigtask.Invoice.Invoice;
import com.bigtask.Payment.PaymentService;
import com.bigtask.PricingPolicy.HappyHoursPricing;
import com.bigtask.PricingPolicy.PricingPolicy;
import com.bigtask.PricingPolicy.StandardPricing;
import com.bigtask.Repositories.*;
import com.bigtask.Resource.*;
import com.bigtask.User.CompanyUser;
import com.bigtask.User.IndividualUser;
import com.bigtask.User.User;
import com.bigtask.valueObjects.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CliApp {
    public static void main(String[] args) {
        ResourceRepository resourceRepository = new InMemoryResourceRepository();
        UserRepository userRepository = new InmemoryUserRepository();
        BookingRepository bookingRepository = new InMemoryBookingRepository(); // upewnij się, że masz tę klasę
        PricingPolicy pricingPolicy = new StandardPricing();
        BookingService bookingService = new BookingService(userRepository, bookingRepository, resourceRepository, pricingPolicy);
        Billable invoiceGenerator = new Invoice("XXXXX", java.time.LocalDateTime.now(), null, null, "");
        PaymentService paymentService = new PaymentService(bookingRepository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Reservation system is working now. Enter HELP to get help");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+");
            String command = parts[0].toUpperCase();


            if (command.equals("QUIT")) {
                System.out.println("OK: System closed.");
                break;
            }


            try {
                switch (command) {
                    case "ADD_USER":
                        handleAddUser(parts, userRepository);
                        System.out.println("OK: User added successfully.");
                        break;

                    case "LIST_USERS":
                        handleListUsers(userRepository);
                        System.out.println("OK: Users list displayed.");
                        break;
                    case "ADD_ROOM":
                        handleAddRoom(parts, resourceRepository);
                        System.out.println("OK: Room added successfully.");
                        break;

                    case "ADD_DESK":
                        handleAddDesk(parts, resourceRepository);
                        System.out.println("OK: Desk added successfully.");
                        break;

                    case "ADD_DEVICE":
                        handleAddDevice(parts, resourceRepository);
                        System.out.println("OK: Device added successfully.");
                        break;

                    case "LIST_RESOURCES":
                        handleListResources(resourceRepository);
                        System.out.println("OK: Resources list displayed.");
                        break;
                    case "BOOK":
                        handleBook(parts, bookingService, userRepository, resourceRepository);
                        break;

                    case "CONFIRM":
                        handleConfirm(parts, bookingService);
                        break;

                    case "CANCEL":
                        handleCancel(parts, bookingService);
                        break;

                    case "LIST_BOOKINGS":
                        handleListBookings(bookingService);
                        System.out.println("OK: Bookings list displayed.");
                        break;
                    case "INVOICE":
                        handleInvoice(parts, bookingRepository, invoiceGenerator);
                        break;
                    case "SET_PRICING":
                        handleSetPricing(parts, bookingService);
                        break;
                    case "PAY":
                        handlePay(parts, paymentService);
                        break;
                    case "HELP":
                        System.out.println("=== AVAILABLE COMMANDS ===");
                        System.out.println("--- USERS ---");
                        System.out.println("ADD_USER INDIVIDUAL <email> <fullName>");
                        System.out.println("ADD_USER COMPANY <email> <displayName> <companyName> <nip>");
                        System.out.println("LIST_USERS");

                        System.out.println("\n--- RESOURCES ---");
                        System.out.println("ADD_ROOM <name> <seats> <hourlyRate>");
                        System.out.println("ADD_DESK <name> <HOT|FIXED> <hourlyRate>");
                        System.out.println("ADD_DEVICE <name> <quantity> <hourlyRate>");
                        System.out.println("LIST_RESOURCES");

                        System.out.println("\n--- PRICING ---");
                        System.out.println("SET_PRICING <STANDARD|HAPPY_HOURS>");

                        System.out.println("\n--- BOOKINGS ---");
                        System.out.println("BOOK <userEmail> <resourceName> <startIso> <endIso OR durationMinutes>");
                        System.out.println("CONFIRM <bookingId>");
                        System.out.println("CANCEL <bookingId>");
                        System.out.println("LIST_BOOKINGS");

                        System.out.println("\n--- PAYMENTS & INVOICES ---");
                        System.out.println("PAY <bookingId> CARD <last4>");
                        System.out.println("INVOICE <bookingId>");

                        System.out.println("\n--- SYSTEM ---");
                        System.out.println("HELP - display this message");
                        System.out.println("QUIT - close the application");
                        System.out.println("==========================");
                        break;

                    default:
                        throw new IllegalArgumentException("Unknown command: " + command);
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        scanner.close();

    }

    private static void handleAddUser(String[] parts, UserRepository userRepository) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Missing arguments. Format: ADD_USER INDIVIDUAL <email> <fullName>");
        }

        String type = parts[1].toUpperCase();
        String email = parts[2];

        if (type.equals("INDIVIDUAL")) {
            String fullName = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
            User user = new IndividualUser(email, fullName);
            userRepository.addUser(user);

        } else if (type.equals("COMPANY")) {
            if (parts.length < 6) {
                throw new IllegalArgumentException("Missing arguments. Format: ADD_USER COMPANY <email> <displayName_without_spaces> <companyName> <nip>");
            }

            String displayName = parts[3].replace("_", " ");

            String nip = parts[parts.length - 1];

            String companyName = String.join(" ", Arrays.copyOfRange(parts, 4, parts.length - 1));

            User user = new CompanyUser(email, displayName, companyName, nip);
            userRepository.addUser(user);

        } else {
            throw new IllegalArgumentException("Unknown user type: " + type + ". Format: INDIVIDUAL or COMPANY.");
        }
    }

    private static void handleListUsers(UserRepository userRepository) {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new IllegalStateException("No users found.");
        }

        for (User user : users) {
            System.out.println("- " + user.toString());
        }
    }

    private static void handleAddRoom(String[] parts, ResourceRepository resourceRepository) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Missing arguments. Format: ADD_ROOM <name> <seats> <hourlyRate>");
        }

        String name = parts[1];
        int seats = Integer.parseInt(parts[2]);
        BigDecimal rate = new BigDecimal(parts[3]);

        Resource room = new Room(name, new Money(rate), seats, new java.util.HashSet<>());
        resourceRepository.add(room);
    }

    private static void handleAddDesk(String[] parts, ResourceRepository resourceRepository) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Missing arguments. Format: ADD_DESK <name> <hot|fixed> <hourlyRate>");
        }

        String name = parts[1];
        DeskEnum type = DeskEnum.valueOf(parts[2].toUpperCase());
        BigDecimal rate = new BigDecimal(parts[3]);

        Resource desk = new Desk(name, new Money(rate), type);
        resourceRepository.add(desk);
    }

    private static void handleAddDevice(String[] parts, ResourceRepository resourceRepository) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Missing arguments. Format: ADD_DEVICE <name> <quantity> <hourlyRate>");
        }

        String name = parts[1];
        int quantity = Integer.parseInt(parts[2]);
        BigDecimal rate = new BigDecimal(parts[3]);

        Resource device = new Device(name, new Money(rate), quantity);
        resourceRepository.add(device);
    }

    private static void handleListResources(ResourceRepository resourceRepository) {
        List<Resource> resources = resourceRepository.findAll();

        if (resources.isEmpty()) {
            throw new IllegalStateException("No resources found in the system.");
        }

        for (Resource resource : resources) {
            System.out.println("- " + resource.describe());
        }
    }

    private static void handleBook(String[] parts, BookingService bookingService, UserRepository userRepository, ResourceRepository resourceRepository) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Missing arguments. Format: BOOK <userEmail> <resourceName> <startIso> <endIso OR durationMinutes>");
        }

        String email = parts[1];
        String resourceName = parts[2];
        LocalDateTime start = LocalDateTime.parse(parts[3]);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        Resource resource = resourceRepository.findByName(resourceName)
                .orElseThrow(() -> new IllegalArgumentException("Resource not found with name: " + resourceName));

        Booking booking;


        try {
            int durationMinutes = Integer.parseInt(parts[4]);
            booking = bookingService.book(user, resource, start, durationMinutes);
        } catch (NumberFormatException e) {
            LocalDateTime end = LocalDateTime.parse(parts[4]);
            booking = bookingService.book(user, resource, start, end);
        }

        System.out.println("OK: Booking created successfully. ID: " + booking.getId() + " | Price: " + booking.getCalculatedPrice());
    }

    private static void handleConfirm(String[] parts, BookingService bookingService) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing arguments. Format: CONFIRM <bookingId>");
        }
        String bookingId = parts[1];
        bookingService.confirm(bookingId);
        System.out.println("OK: Booking " + bookingId + " confirmed.");
    }

    private static void handleCancel(String[] parts, BookingService bookingService) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing arguments. Format: CANCEL <bookingId>");
        }
        String bookingId = parts[1];
        bookingService.cancel(bookingId);
        System.out.println("OK: Booking " + bookingId + " cancelled.");
    }

    private static void handleListBookings(BookingService bookingService) {
        List<Booking> bookings = bookingService.list(null, null, null);

        if (bookings.isEmpty()) {
            throw new IllegalStateException("No bookings found in the system.");
        }

        for (Booking booking : bookings) {
            System.out.printf("- Booking ID: %s | User: %s | Resource: %s | Status: %s | Start: %s | End: %s%n",
                    booking.getId(),
                    booking.getUser().getEmail(),
                    booking.getResource().getName(),
                    booking.getStatus(),
                    booking.getStart(),
                    booking.getEnd());
        }
    }

    private static void handleInvoice(String[] parts, BookingRepository bookingRepository, Billable invoiceGenerator) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing arguments. Format: INVOICE <bookingId>");
        }

        String bookingId = parts[1];

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingId));

        if (booking.getStatus() != com.bigtask.Booking.BookingStatus.CONFIRMED &&
                booking.getStatus() != com.bigtask.Booking.BookingStatus.COMPLETED) {
            throw new IllegalStateException("Invoice can only be generated for CONFIRMED or COMPLETED bookings.");
        }

        Invoice generatedInvoice = invoiceGenerator.toInvoice(booking);

        System.out.println("OK: Invoice generated successfully.");
        System.out.println("- " + generatedInvoice.toString());

    }

    private static void handleSetPricing(String[] parts, BookingService bookingService) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing arguments. Format: SET_PRICING STANDARD|HAPPY_HOURS");
        }

        String policyType = parts[1].toUpperCase();

        if (policyType.equals("STANDARD")) {
            bookingService.setPricingPolicy(new StandardPricing());
            System.out.println("OK: Pricing policy changed to STANDARD.");

        } else if (policyType.equals("HAPPY_HOURS")) {
            bookingService.setPricingPolicy(new HappyHoursPricing());
            System.out.println("OK: Pricing policy changed to HAPPY_HOURS.");

        } else {
            throw new IllegalArgumentException("Unknown pricing policy: " + policyType + ". Use STANDARD or HAPPY_HOURS.");
        }
    }

    private static void handlePay(String[] parts, PaymentService paymentService) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Missing arguments. Format: PAY <bookingId> CARD <last4>");
        }

        String bookingId = parts[1];
        String paymentMethod = parts[2].toUpperCase();
        String last4 = parts[3];

        if (!paymentMethod.equals("CARD")) {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod + ". Only CARD is supported.");
        }

        paymentService.pay(bookingId, last4);

        System.out.println("OK: Payment captured successfully for booking " + bookingId + ".");
    }



}
