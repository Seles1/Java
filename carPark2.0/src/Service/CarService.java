package Service;

import Domain.Car;
import Domain.Reservation;
import Exceptions.RepositoryException;
import Exceptions.ServiceException;
import Filter.AbstractFilter;
import Repository.CarRepository;
import Repository.FilteredRepository;
import Repository.IRepository;
import Repository.ReservationRepository;

import java.util.ArrayList;
import java.util.logging.Filter;

public class CarService {
    private final IRepository<Integer, Car> carRepository;
    private final IRepository<Integer, Reservation> reservationRepository;

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
            Car car = carRepository.findById(oldId);

            if (car == null) {
                throw new ServiceException("Car with ID " + oldId + " not found.");
            }
            car.setBrand(brand);
            car.setModel(model);
            car.setPrice(price);
            car.setColor(color);
            try {
                carRepository.modify(car);
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

    public Car findById(Integer id) {
        try {
            return carRepository.findById(id);
        } catch (RepositoryException e) {
            return null;
        }
    }

    public void deleteCar(Integer id) throws ServiceException {
        boolean hasReservation = false;
        for (Reservation res : reservationRepository.getAll()) {
            if (res.getCarId().equals(id)) {
                hasReservation = true;
                break;
            }
        }
        if (hasReservation) {
            throw new ServiceException("Cannot delete car " + id + ". It has existing reservations.");
        }
        try {
            carRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to delete car: " + e.getMessage());
        }
    }

    public Iterable<Car> getFilteredCars(AbstractFilter<Car> filter) {
        FilteredRepository<Integer, Car> filteredRepository = new FilteredRepository<>(carRepository, filter);
        return filteredRepository.getAll();
    }
}
