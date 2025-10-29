package Service;

import Domain.Car;
import Domain.Reservation;
import Filter.AbstractFilter;
import Repository.CarRepository;
import Repository.FilteredRepository;
import Repository.ReservationRepository;

import java.time.LocalDate;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;

    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
    }

    public void addReservation(Integer carId, String customerName, LocalDate startDate, LocalDate endDate) throws Exception {
        Car car = carRepository.findById(carId);
        if (car == null) {
            throw new Exception("Car with Id: " + carId + " does not exist.");
        }
        if (startDate.isAfter(endDate)) {
            throw new Exception("Invalid reservation dates. Start date cannot be after end date.");
        }
        validateAvailability(carId, startDate, endDate, null);
        Reservation reservation = new Reservation(null, carId, customerName, startDate, endDate);
        reservationRepository.add(reservation);
    }

    public void updateReservation(Integer id, Integer carId, String customerName, LocalDate startDate, LocalDate endDate) throws Exception {
        Reservation existingReservation = reservationRepository.findById(id);
        if (existingReservation == null) {
            throw new Exception("Reservation with Id: " + id + " does not exist.");
        }
        Car car = carRepository.findById(carId);
        if (car == null) {
            throw new Exception("Car with Id: " + carId + " does not exist.");
        }
        if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
            throw new Exception("Invalid reservation dates. Start date cannot be after end date.");
        }
        validateAvailability(carId, startDate, endDate, id);
        existingReservation.setCarId(carId);
        existingReservation.setCustomerName(customerName);
        existingReservation.setStartDate(startDate);
        existingReservation.setEndDate(endDate);
        reservationRepository.modify(existingReservation);
    }

    public void deleteReservation(Integer id) throws Exception {
        reservationRepository.delete(id);
    }

    public Reservation findById(Integer id) {
        return reservationRepository.findById(id);
    }

    public Iterable<Reservation> getAll() {
        return reservationRepository.getAll();
    }

    public Iterable<Reservation> getFilteredReservations(AbstractFilter<Reservation> filter) {
        FilteredRepository<Integer, Reservation> filteredRepository = new FilteredRepository<>(reservationRepository, filter);
        return filteredRepository.getAll();
    }

    private void validateAvailability(Integer carId, LocalDate startDate, LocalDate endDate, Integer id) throws Exception {
        for (Reservation reservation : reservationRepository.getAll()) {
            if (reservation.getId().equals(id)) {
                continue;
            }
            if (reservation.getCarId().equals(carId)) {
                if (!(reservation.getEndDate().isBefore(startDate) || endDate.isBefore(reservation.getStartDate()))) {
                    throw new Exception("Car is already reserved during this period.");
                }
            }
        }
    }
}
