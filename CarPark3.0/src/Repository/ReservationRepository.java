package Repository;

import Domain.Reservation;
import Exception.RepositoryException;

public class ReservationRepository extends MemoryRepository<Integer, Reservation> {
    private Integer
    nextAvailableId=1;

    @Override
    public void add(Reservation entity) throws RepositoryException {
        entity.setId(nextAvailableId++);
        super.add(entity);
    }
}
