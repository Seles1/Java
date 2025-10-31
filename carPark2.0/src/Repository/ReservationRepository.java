package Repository;

import Domain.Reservation;
import Exceptions.RepositoryException;

import java.time.LocalDate;

public class ReservationRepository extends MemoryRepository<Integer, Reservation> {
    private Integer nextAvailableID = 1;

    public ReservationRepository() throws Exception{
        this.add(new Reservation(null, 1, "Alexandru Voda", LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15)));
        this.add(new Reservation(null, 2, "Stefan Cel Mare", LocalDate.of(2025, 1, 20), LocalDate.of(2025, 1, 22)));
        this.add(new Reservation(null, 3, "Ion Creanga", LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 25)));
        this.add(new Reservation(null, 1, "Mihai Eminescu", LocalDate.of(2025, 1, 18), LocalDate.of(2025, 1, 30)));
        this.add(new Reservation(null, 4, "Ion Barbu", LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 15)));
    }

    @Override
    public void add(Reservation entity) throws RepositoryException {
        entity.setId(nextAvailableID++);
        super.add(entity);
    }
}
