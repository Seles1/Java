package Tests;

import Domain.Car;
import Exceptions.RepositoryException;
import Repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;


public class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testConstructor() {
        Assertions.assertNotNull(carRepository);
        Car car = carRepository.findById(1);
        Assertions.assertEquals(car, carRepository.findById(1));
        Assertions.assertNotNull(carRepository.findById(5));
    }


    @Test
    void testAddCar() throws RepositoryException {
        Car newCar = new Car(null, "Ford", "Mustang", 700, "Yellow");
        carRepository.add(newCar);
        Assertions.assertEquals(6, newCar.getId());
        Assertions.assertNotNull(carRepository.findById(6));
    }

    @Test
    void testModifyCar() throws RepositoryException {
        Car oldCar = carRepository.findById(1);
        Car updatedCar = new Car(1, "Mazda", "RX-8", 250, "Blue");
        carRepository.modify(updatedCar);
        Assertions.assertEquals(updatedCar, carRepository.findById(1));
        Assertions.assertNotEquals(oldCar, carRepository.findById(1));
    }

    @Test
    void testModifyNullCar() throws RepositoryException {
        try {
            Car updatedCar = new Car(8, "Mazda", "RX-8", 250, "Blue");
            carRepository.modify(updatedCar);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void testDeleteCar() throws RepositoryException {
        carRepository.delete(1);
        Assertions.assertNull(carRepository.findById(1));
    }

    @Test
    void testDeleteNullCar() throws RepositoryException {
        try {
            carRepository.delete(8);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    void testFindById() throws RepositoryException {
        Car car = carRepository.findById(1);
        Assertions.assertEquals(car, carRepository.findById(1));
        Assertions.assertEquals("Tesla",carRepository.findById(5).getBrand());
    }

    @Test
    void testFindByIdNull() throws RepositoryException {
        Car car = carRepository.findById(9);
        Assertions.assertNull(car);
    }

    @Test
    void testGetAll() throws RepositoryException {
        Assertions.assertNotNull(carRepository.getAll());
        Iterable<Car> carList = carRepository.getAll();
        for (Car car : carList) {
            Assertions.assertNotNull(carRepository.findById(car.getId()));
        }
    }


}
