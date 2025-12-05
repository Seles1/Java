package Service;

import Domain.Car;
import Domain.Reservation;
import Exceptions.RepositoryException;
import Exceptions.ServiceException;
import Filter.AbstractFilter;
import Repository.*;

import java.util.*;
import java.util.stream.*;

public class CarService {
    private final IRepository<Integer, Car> carRepository;
    private final IRepository<Integer, Reservation> reservationRepository;

    public CarService() {
        carRepository = new CarRepository();
        reservationRepository = new ReservationRepository();
    }

    public CarService(IRepository<Integer, Car> carRepository, IRepository<Integer, Reservation> reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public void addCar(String brand, String model, int price, String color) throws ServiceException {
        Car car = new Car(null, brand, model, price, color);
        try {
            carRepository.add(car);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to add car: " + e.getMessage());
        }
    }

    public void updateCar(Integer oldId, String brand, String model, int price, String color) throws ServiceException {
        try {
            Optional<Car> car = carRepository.findById(oldId);

            if (car.isEmpty()) {
                throw new ServiceException("Car with ID " + oldId + " not found.");
            }
            car.get().setBrand(brand);
            car.get().setModel(model);
            car.get().setPrice(price);
            car.get().setColor(color);
            try {
                carRepository.modify(car.get());
            } catch (RepositoryException e) {
                throw new ServiceException("Failed to update car: " + e.getMessage());
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to update car: " + e.getMessage());
        }
    }

    public Iterable<Car> getAll() {
        return carRepository.getAll();
    }

    public List<Car> getAllCars() {
        Iterable<Car> cars = carRepository.getAll();
        List<Car> carList = new ArrayList<>();
        cars.forEach(carList::add);
        return carList;
    }

    public Optional<Car> findById(Integer id) {
        try {
            return carRepository.findById(id);
        } catch (RepositoryException e) {
            return null;
        }
    }

    public void deleteCar(Integer id) throws ServiceException {
        reservationRepository.getAll().forEach(reservation -> {
            if (reservation.getCarId().equals(id)) {
                try {
                    reservationRepository.delete(reservation.getId());
                } catch (RepositoryException e) {
                    throw new RuntimeException("Reservation deletion failed" + e.getMessage());
                }
            }
        });
        try {
            carRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Car deletion failed" + e.getMessage());
        }
    }

    public List<Car> getCarsRentedByCustomer(String customerName) {
        List<Reservation> reservations = new ArrayList<Reservation>();
        reservationRepository.getAll().forEach(reservations::add);
        return reservations.stream()
                .filter(reservation -> reservation.getCustomerName().equals(customerName))
                .map(reservation -> {
                    try {
                        Optional<Car> car = carRepository.findById(reservation.getCarId());
                        if (car.isPresent()) {
                            return car.get();
                        } else return null;
                    } catch (RepositoryException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Car> getCarsOfAGivenColor(String color) {
        List<Car> cars = new ArrayList<Car>();
        carRepository.getAll().forEach(cars::add);
        return cars.stream()
                .filter(car -> car.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public List<Car> getCarsSortedDescendingByPrice() {
        List<Car> cars = new ArrayList<Car>();
        carRepository.getAll().forEach(cars::add);
        return cars.stream()
                .sorted(Comparator.comparing(Car::getPrice).reversed())
                .collect(Collectors.toList());
    }


    public Iterable<Car> getFilteredCars(AbstractFilter<Car> filter) {
        FilteredRepository<Integer, Car> filteredRepository = new FilteredRepository<>(carRepository, filter);
        return filteredRepository.getAll();
    }
}
