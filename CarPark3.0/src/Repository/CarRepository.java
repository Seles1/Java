package Repository;

import Domain.Car;
import Exception.RepositoryException;

public class CarRepository extends MemoryRepository<Integer, Car> {
    private Integer nextAvailableId = 1;

    @Override
    public void add(Car entity) throws RepositoryException {
        entity.setId(nextAvailableId++);
        super.add(entity);
    }
}
