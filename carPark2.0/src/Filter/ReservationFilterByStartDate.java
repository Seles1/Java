package Filter;

import Domain.Reservation;

import java.time.LocalDate;

public class ReservationFilterByStartDate implements AbstractFilter<Reservation> {
    private final LocalDate minstartDate;

    public ReservationFilterByStartDate(LocalDate minstartDate) {
        this.minstartDate = minstartDate;
    }

    @Override
    public boolean accept(Reservation entity) {
        return !entity.getStartDate().isBefore(minstartDate);
    }
}
