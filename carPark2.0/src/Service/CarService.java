package Service;

import Domain.Car;
import Domain.Reservation;
import Filter.AbstractFilter;
import Repository.CarRepository;
import Repository.FilteredRepository;
import Repository.ReservationRepository;

import java.util.ArrayList;
import java.util.logging.Filter;

public class CarService {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public CarService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public void addCar(String brand, String model, int price, String color) throws Exception {
        Car car = new Car(null, brand, model, price, color);
        carRepository.add(car);
    }

    public void updateCar(Integer oldId, String brand, String model, int price, String color) throws Exception {
        Car car = carRepository.findById(oldId);
        if (car == null) {
            throw new Exception("Car with ID " + oldId + " not found.");
        }
        car.setBrand(brand);
        car.setModel(model);
        car.setPrice(price);
        car.setColor(color);
        carRepository.modify(car);
    }

    public Iterable<Car> getAll() {
        return carRepository.getAll();
    }

    public Car findById(Integer id) {
        return carRepository.findById(id);
    }

    public void deleteCar(Integer id) throws Exception {
        boolean hasReservation = false;
        for (Reservation res : reservationRepository.getAll()) {
            if (res.getCarId().equals(id)) {
                hasReservation = true;
                break;
            }
        }
        if (hasReservation) {
            throw new Exception("Cannot delete car " + id + ". It has existing reservations.");
        }
        carRepository.delete(id);
    }

    public Iterable<Car> getFilteredCars(AbstractFilter<Car> filter) {
        FilteredRepository<Integer, Car> filteredRepository = new FilteredRepository<>(carRepository, filter);
        return filteredRepository.getAll();
    }
}
