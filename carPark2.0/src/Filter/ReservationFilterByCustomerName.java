package Filter;

import Domain.Reservation;

public class ReservationFilterByCustomerName implements AbstractFilter<Reservation> {
    private final String requiredNamePart;

    public ReservationFilterByCustomerName(String requiredName) {
        this.requiredNamePart = requiredName;
    }

    @Override
    public boolean accept(Reservation entity) {
        return entity.getCustomerName().contains(this.requiredNamePart);
    }
}
