package UI;

import Domain.Car;
import Domain.Reservation;
import Exceptions.ServiceException;
import Filter.AbstractFilter;
import Filter.CarFilterByBrand;
import Filter.CarFilterByMaximumPrice;
import Filter.ReservationFilterByCustomerName;
import Filter.ReservationFilterByStartDate;
import Repository.*;
import Service.CarService;
import Service.ReservationService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

public class UI {
    private final CarService carService;
    private final ReservationService reservationService;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public UI(CarService carService, ReservationService reservationService) {
        this.carService = carService;
        this.reservationService = reservationService;
    }

    public void run() throws Exception {
        boolean running = true;
        while (running) {
            System.out.println("1. Car Operations");
            System.out.println("2. Reservation Operations");
            System.out.println("3. Filter Cars");
            System.out.println("4. Filter Reservations");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        carMenu();
                        break;
                    case "2":
                        reservationMenu();
                        break;
                    case "3":
                        filterCars();
                        break;
                    case "4":
                        filterReservations();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
            }
        }
    }

    private void carMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Add Car");
            System.out.println("2. Update Car");
            System.out.println("3. Delete Car");
            System.out.println("4. View Car by ID");
            System.out.println("5. View All Cars");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        addCar();
                        break;
                    case "2":
                        updateCar();
                        break;
                    case "3":
                        deleteCar();
                        break;
                    case "4":
                        viewCarById();
                        break;
                    case "5":
                        viewAll(carService.getAll());
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Car operation failed: " + e.getMessage());
            }
        }
    }

    private void addCar() throws Exception {
        System.out.print("Enter Car Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter Car Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Rental Price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Color: ");
        String color = scanner.nextLine();
        carService.addCar(brand, model, price, color);
        System.out.println("Car added successfully.");
    }

    private void updateCar() throws Exception {
        System.out.print("Enter ID of car to be updated: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        Optional<Car> oldCar = carService.findById(id);
        if (oldCar.isEmpty()) {
            System.out.println("Car with ID " + id + " not found.");
            return;
        }
        System.out.println("Current car: " + oldCar);
        System.out.print("Enter New Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter New Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter New Price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter New Color: ");
        String color = scanner.nextLine();
        carService.updateCar(id, brand, model, price, color);
        System.out.println("Car with ID " + id + " updated successfully.");
    }

    private void deleteCar() throws Exception {
        System.out.print("Enter ID of car to be deleted: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        carService.deleteCar(id);
        System.out.println("Car deleted successfully.");
    }

    private void viewCarById() {
        System.out.print("Enter ID of car to be viewed: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        Optional<Car> car = carService.findById(id);
        if (car.isEmpty()) {
            System.out.println("Car with ID " + id + " not found.");
        } else {
            System.out.println("Car: " + car);
        }
    }

    private void filterCars() {
        System.out.println("1. Filter by Brand");
        System.out.println("2. Filter by Maximum Price");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        try {
            switch (choice) {
                case "1":
                    System.out.print("Enter Car Brand to search for: ");
                    String make = scanner.nextLine();
                    viewAll(carService.getFilteredCars(new CarFilterByBrand(make)));
                    break;
                case "2":
                    System.out.print("Enter Maximum Price: ");
                    int maxPrice = Integer.parseInt(scanner.nextLine());
                    viewAll(carService.getFilteredCars(new CarFilterByMaximumPrice(maxPrice)));
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Invalid number format entered.");
        }
    }

    private void reservationMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Add Reservation");
            System.out.println("2. Update Reservation");
            System.out.println("3. Delete Reservation");
            System.out.println("4. View Reservation by ID");
            System.out.println("5. View All Reservations");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        addReservation();
                        break;
                    case "2":
                        updateReservation();
                        break;
                    case "3":
                        deleteReservation();
                        break;
                    case "4":
                        viewReservationById();
                        break;
                    case "5":
                        viewAll(reservationService.getAll());
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Reservation operation failed: " + e.getMessage());
            }
        }
    }

    private LocalDate readDate(String prompt) throws Exception {
        System.out.print(prompt + " (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        return LocalDate.parse(dateString, dateFormatter);
    }

    private void addReservation() throws Exception {
        System.out.print("Enter Car ID for Reservation: ");
        Integer carId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        LocalDate startDate = readDate("Enter Start Date");
        LocalDate endDate = readDate("Enter End Date");

        reservationService.addReservation(carId, customerName, startDate, endDate);
        System.out.println("Reservation added successfully.");
    }

    private void updateReservation() throws Exception {
        System.out.print("Enter ID of reservation to be updated: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        Optional<Reservation> oldRes = reservationService.findById(id);
        if (oldRes.isEmpty()) {
            System.out.println("Reservation with ID " + id + " not found.");
            return;
        }
        System.out.println("Current reservation: " + oldRes);
        System.out.print("Enter New Car ID: ");
        int carId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter New Customer Name: ");
        String customerName = scanner.nextLine();
        LocalDate startDate = oldRes.get().getStartDate();
        LocalDate endDate = oldRes.get().getEndDate();
        try {
            startDate = readDate("Enter New Start Date: ");
        } catch (Exception e) {
            throw new Exception("Invalid start date entered.");
        }
        try {
            endDate = readDate("Enter New End Date: ");
        } catch (Exception e) {
            throw new Exception("Invalid end date entered.");
        }
        reservationService.updateReservation(id, carId, customerName, startDate, endDate);
        System.out.println("Reservation with ID " + id + " updated successfully.");
    }

    private void deleteReservation() throws Exception {
        System.out.print("Enter ID of reservation to be deleted: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        reservationService.deleteReservation(id);
        System.out.println("Reservation deleted successfully.");
    }

    private void viewReservationById() throws ServiceException {
        System.out.print("Enter ID of reservation to be viewed: ");
        Integer id = Integer.parseInt(scanner.nextLine());
        Optional<Reservation> reservation = reservationService.findById(id);
        if (reservation.isEmpty()) {
            System.out.println("Reservation with ID " + id + " not found.");
        } else {
            System.out.println("Reservation: " + reservation);
        }
    }

    private void filterReservations() {
        System.out.println("1. Filter by Customer Name");
        System.out.println("2. Filter by Start Date");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1":
                    System.out.print("Enter part of Customer Name: ");
                    String namePart = scanner.nextLine();
                    viewAll(reservationService.getFilteredReservations(new ReservationFilterByCustomerName(namePart)));
                    break;
                case "2":
                    LocalDate startDate = readDate("Enter Minimum Start Date");
                    viewAll(reservationService.getFilteredReservations(new ReservationFilterByStartDate(startDate)));
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        }
    }


    private <T> void viewAll(Iterable<T> items) {
        if (!items.iterator().hasNext()) {
            System.out.println("No items found matching the criteria.");
            return;
        }
        System.out.println("Results:");
        for (T item : items) {
            System.out.println(item);
        }
    }
}
