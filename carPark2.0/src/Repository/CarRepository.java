package Repository;

import Domain.Car;
import Exceptions.RepositoryException;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;


public class CarRepository extends MemoryRepository<Integer, Car> {
    private Integer NextAvailableID = 1;

    public CarRepository() {
        try {
            this.add(new Car(null, "Volkswagen", "Scirocco", 300, "Blue"));
            this.add(new Car(null, "BMW", "5 Series", 500, "Black"));
            this.add(new Car(null, "Volvo", "S60", 350, "Grey"));
            this.add(new Car(null, "Audi", "A4", 400, "White"));
            this.add(new Car(null, "Tesla", "Model Y", 450, "Red"));
        } catch (RepositoryException e) {
            System.out.println("Error adding initial cars: " + e.getMessage());
        }
    }

    @Override
    public void add(Car entity) throws RepositoryException {
        entity.setId(NextAvailableID++);
        super.add(entity);
    }
}
