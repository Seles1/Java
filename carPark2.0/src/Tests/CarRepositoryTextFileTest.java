package Tests;

import Domain.Car;
import Exceptions.RepositoryException;
import Repository.CarRepositoryTextFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.Option;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Optional;

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
        Optional<Car> car1 = carRepository.findById(1);
        Assertions.assertNotNull(car1);
        Assertions.assertTrue(car1.isPresent());
        Assertions.assertEquals("Opel", car1.get().getBrand());
        Assertions.assertEquals(150, car1.get().getPrice());
        Optional<Car> car2 = carRepository.findById(2);
        Assertions.assertNotNull(car2);
        Assertions.assertTrue(car2.isPresent());
        Assertions.assertEquals("Volvo", car2.get().getBrand());
    }

    @Test
    void testAddCar() throws RepositoryException {
        Car newCar = new Car(null, "Tesla", "Model S", 800, "Red");
        carRepository.add(newCar);
        Optional<Car> car3=carRepository.findById(3);
        Assertions.assertTrue(car3.isPresent());
        Assertions.assertEquals(3, car3.get().getId());
        Assertions.assertNotNull(carRepository.findById(3));
        Integer id = null;
        for (Car car : carRepository.getAll()) {
            id = car.getId();
        }
        Assertions.assertEquals(3, id);
    }



    @Test
    void testModifyCar() throws RepositoryException {
        Car updatedCar = new Car(1, "Updated_Opel", "Updated_Astra", 999, "Updated_Color");
        carRepository.modify(updatedCar);
        Optional<Car> result = carRepository.findById(1);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Updated_Opel", result.get().getBrand());
        Assertions.assertEquals(999, result.get().getPrice());
    }

    @Test
    void testWriteToFileConsistency() throws Exception {
        carRepository.add(new Car(null, "Mazda", "MX-5", 400, "Black")); // ID 3
        carRepository.delete(1);
        CarRepositoryTextFile newRepo = new CarRepositoryTextFile(testFile);

        Optional<Car> car2=newRepo.findById(2);
        Optional<Car> car3=newRepo.findById(3);
        try{
            Optional<Car> car1=newRepo.findById(1);
            assert false;
        }catch(NullPointerException e){
            assert true;
        }
        Assertions.assertTrue(car2.isPresent());
        Assertions.assertTrue(car3.isPresent());
    }
}
