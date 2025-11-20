package Tests;

import Domain.Car;
import Exceptions.RepositoryException;
import Repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;


public class CarRepositoryTest {
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testConstructor() {
        Assertions.assertNotNull(carRepository);
        Optional<Car> car = carRepository.findById(1);
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
        Optional<Car> oldCar = carRepository.findById(1);
        Car updatedCar = new Car(1, "Mazda", "RX-8", 250, "Blue");
        carRepository.modify(updatedCar);
        Optional<Car> car= carRepository.findById(1);
        Assertions.assertTrue(car.isPresent());
        Assertions.assertEquals(updatedCar, car.get());
        Assertions.assertNotEquals(oldCar, car);
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
        Optional<Car> car = carRepository.findById(1);
        Assertions.assertEquals(car, carRepository.findById(1));
        Optional<Car> car5=carRepository.findById(5);
        Assertions.assertTrue(car5.isPresent());
        Assertions.assertEquals("Tesla",car5.get().getBrand());
    }

    @Test
    void testFindByIdNull() throws RepositoryException {
        try {
            Optional<Car> car = carRepository.findById(9);
            assert false;
        }catch (NullPointerException e)
        {
            assert true;
        }
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
