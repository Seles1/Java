package Filter;

import Domain.Car;

public class CarFilterByMaximumPrice implements AbstractFilter<Car> {
    private final int maxPrice;

    public CarFilterByMaximumPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean accept(Car entity) {
        return entity.getPrice() <= maxPrice;
    }
}
