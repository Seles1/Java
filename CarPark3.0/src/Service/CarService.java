package Service;

import Domain.Car;
import Domain.Reservation;
import Repository.CarRepository;
import Repository.IRepository;
import Repository.ReservationRepository;
import Exception.ServiceException;
import Exception.RepositoryException;

public class CarService {
    private final IRepository<Integer, Car> carRepository;
    private final IRepository<Integer, Reservation> reservationRepository;

    public CarService(IRepository<Integer, Car> carRepository, IRepository<Integer, Reservation> reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public void addCar(String brand, String model, int price, String color) throws ServiceException {
        Car newCar = new Car(null, brand, model, price, color);
        try {
            carRepository.add(newCar);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to add car:" + e.getMessage());
        }
    }
}
