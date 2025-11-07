package Tests;

import Domain.Car;
import org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CarTest {
    @Test
    void testCarConstructorAndGetters() {
        Car car = new Car(10, "Tesla", "Model 3", 500, "White");
        Assertions.assertEquals(10, car.getId());
        Assertions.assertEquals("Tesla", car.getBrand());
        Assertions.assertEquals("Model 3", car.getModel());
        Assertions.assertEquals(500, car.getPrice());
        Assertions.assertEquals("White", car.getColor());
    }

    @Test
    void testSettersAndGetters() {
        Car car = new Car(1, "OldBrand", "OldModel", 100, "OldColor");
        car.setId(2);
        car.setBrand("NewBrand");
        car.setModel("NewModel");
        car.setPrice(200);
        car.setColor("NewColor");
        Assertions.assertEquals(2, car.getId());
        Assertions.assertEquals("NewBrand", car.getBrand());
        Assertions.assertEquals("NewModel", car.getModel());
        Assertions.assertEquals(200, car.getPrice());
        Assertions.assertEquals("NewColor", car.getColor());
    }

    @Test
    void testToStringFormat() {
        Car car = new Car(1, "BMW", "X5", 600, "Black");
        Assertions.assertEquals("1,BMW,X5,600,Black", car.toString());
    }
}