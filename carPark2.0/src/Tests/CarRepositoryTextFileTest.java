package Tests;

import Domain.Car;
import Exceptions.RepositoryException;
import Repository.CarRepositoryTextFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class CarRepositoryTextFileTest {
    private final static String testFile = "src/Tests/testFile.csv";
    private CarRepositoryTextFile carRepository;

    @BeforeEach
    void setUp() throws Exception {
        File file = new File(testFile);
        carRepository = new CarRepositoryTextFile(testFile);
        Car car1=new Car(1,"Opel","Astra",150,"Silver");
        Car car2=new Car(2,"Volvo","V40",180,"Silver");
        carRepository.add(car1);
        carRepository.add(car2);
    }

    @AfterEach
    void cleanUp() throws IOException {
        File file = new File(testFile);
        if (file.exists()) {
            new FileWriter(file, false).close();
        }
    }

    @Test
    void testReadFromFile() {
        Car car1 = carRepository.findById(1);
        Assertions.assertNotNull(car1);
        Assertions.assertEquals("Opel", car1.getBrand());
        Assertions.assertEquals(150, car1.getPrice());
        Car car2 = carRepository.findById(2);
        Assertions.assertNotNull(car2);
        Assertions.assertEquals("Volvo", car2.getBrand());
    }

    @Test
    void testAddCar() throws RepositoryException {
        Car newCar = new Car(null, "Tesla", "Model S", 800, "Red");
        carRepository.add(newCar);
        Assertions.assertEquals(3, carRepository.findById(3).getId());
        Assertions.assertNotNull(carRepository.findById(3));
        Integer id = null;
        for (Car car : carRepository.getAll()) {
            id = car.getId();
        }
        Assertions.assertEquals(3, id);
    }

    @Test
    void testDeleteCar() throws RepositoryException {
        carRepository.delete(1);
        Assertions.assertNull(carRepository.findById(1));
    }

    @Test
    void testModifyCar() throws RepositoryException {
        Car updatedCar = new Car(1, "Updated_Opel", "Updated_Astra", 999, "Updated_Color");
        carRepository.modify(updatedCar);
        Car result = carRepository.findById(1);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Updated_Opel", result.getBrand());
        Assertions.assertEquals(999, result.getPrice());
    }

    @Test
    void testWriteToFileConsistency() throws Exception {
        carRepository.add(new Car(null, "Mazda", "MX-5", 400, "Black")); // ID 3
        carRepository.delete(1);
        CarRepositoryTextFile newRepo = new CarRepositoryTextFile(testFile);
        Assertions.assertNull(newRepo.findById(1));
        Assertions.assertNotNull(newRepo.findById(2));
        Assertions.assertNotNull(newRepo.findById(3));
    }
}
