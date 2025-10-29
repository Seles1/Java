package Filter;

import Domain.Car;

public class CarFilterByBrand implements AbstractFilter<Car> {
    private final String requiredBrand;

    public CarFilterByBrand(String requiredBrand) {
        this.requiredBrand = requiredBrand;
    }

    @Override
    public boolean accept(Car entity) {
        return entity.getBrand().contains(requiredBrand);
    }
}
