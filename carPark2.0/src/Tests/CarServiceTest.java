package Tests;

import Domain.Car;
import Exceptions.ServiceException;
import Repository.CarRepository;
import Repository.ReservationRepository;
import Service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {
    private CarRepository carRepository;
    private ReservationRepository reservationRepository;
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
        reservationRepository = new ReservationRepository();
        carService = new CarService(carRepository, reservationRepository);
    }

    @Test
    void testAddCar() throws ServiceException {
        carService.addCar("Skoda", "Octavia", 250, "Grey");
        Car addedCar = carService.findById(6);
        assertNotNull(addedCar);
        assertEquals("Skoda", addedCar.getBrand());
    }

    @Test
    void testUpdateCar() throws ServiceException {
        Integer targetId = 1;
        carService.updateCar(targetId, "VW", "NewScirocco", 999, "BrightBlue");
        Car updatedCar = carService.findById(targetId);
        assertNotNull(updatedCar);
        assertEquals("NewScirocco", updatedCar.getModel());
        assertEquals(999, updatedCar.getPrice());
    }

    @Test
    void testUpdateCarFailsIfNotFound() {
        try {
            carService.updateCar(99, "a", "a", 1, "a");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void testDeleteCar() throws ServiceException {
        Integer carIdToDelete = 5;
        carService.deleteCar(carIdToDelete);
        assertNull(carService.findById(carIdToDelete));
    }

    @Test
    void testDeleteNullCar() {
        try {
            carService.deleteCar(99);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void testFindById() {
        Car result = carService.findById(3);
        assertNotNull(result);
        assertEquals("Volvo", result.getBrand());
    }

}