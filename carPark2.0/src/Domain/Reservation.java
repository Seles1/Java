package Domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Identifiable<Integer>, Serializable {
    private Integer id;
    private Integer carId;
    private String customerName;
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(Integer id, Integer carId, String customerName, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.carId = carId;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return id + "," + carId + "," + customerName + "," + startDate + "," + endDate;
    }
}
