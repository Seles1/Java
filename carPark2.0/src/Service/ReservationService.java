package Service;

import Domain.Car;
import Domain.Reservation;
import Exceptions.RepositoryException;
import Exceptions.ServiceException;
import Filter.AbstractFilter;
import Repository.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

public class ReservationService {
    private final IRepository<Integer, Reservation> reservationRepository;
    private final IRepository<Integer, Car> carRepository;

    public ReservationService() {
        reservationRepository = new ReservationRepository();
        carRepository = new CarRepository();
    }

    public ReservationService(IRepository<Integer, Reservation> reservationRepository, IRepository<Integer, Car> carRepository) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
    }

    public void addReservation(Integer carId, String customerName, LocalDate startDate, LocalDate endDate) throws ServiceException {
        try {
            Optional<Car> car = carRepository.findById(carId);
            if (car == null) {
                throw new ServiceException("Car with Id: " + carId + " does not exist.");
            }
            if (startDate.isAfter(endDate)) {
                throw new ServiceException("Invalid reservation dates. Start date cannot be after end date.");
            }
            validateAvailability(carId, startDate, endDate, null);
            Reservation reservation = new Reservation(null, carId, customerName, startDate, endDate);
            try {
                reservationRepository.add(reservation);
            } catch (RepositoryException e) {
                throw new ServiceException("Reservation addition failed: " + e.getMessage());
            }
        } catch (RepositoryException e) {
            throw new ServiceException("Reservation addition failed: " + e.getMessage());
        }
    }

        public void updateReservation(Integer id, Integer carId, String customerName, LocalDate startDate, LocalDate endDate) throws ServiceException {
            try {
                Optional<Reservation> existingReservationOptional = reservationRepository.findById(id);
                if (existingReservationOptional.isEmpty()) {
                    throw new ServiceException("Reservation with Id: " + id + " does not exist.");
                }
                Reservation existingReservation=existingReservationOptional.get();
                Optional<Car> car = carRepository.findById(carId);
                if (car.isEmpty()) {
                    throw new ServiceException("Car with Id: " + carId + " does not exist.");
                }
                if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
                    throw new ServiceException("Invalid reservation dates. Start date cannot be after end date.");
                }
                validateAvailability(carId, startDate, endDate, id);
                existingReservation.setCarId(carId);
                existingReservation.setCustomerName(customerName);
                existingReservation.setStartDate(startDate);
                existingReservation.setEndDate(endDate);
                try {
                    reservationRepository.modify(existingReservation);
                } catch (RepositoryException e) {
                    throw new ServiceException("Reservation modification failed: " + e.getMessage());
                }
            } catch (RepositoryException e) {
                throw new ServiceException("Reservation modification failed: " + e.getMessage());
            }
        }

    public void deleteReservation(Integer id) throws ServiceException {
        try {
            reservationRepository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Reservation deletion failed: " + e.getMessage());
        }
    }

    public Optional<Reservation> findById(Integer id) throws ServiceException {
        try {
            return reservationRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException("findById failed"+ e.getMessage());
        }
    }

    public Iterable<Reservation> getAll() {
        return reservationRepository.getAll();
    }

    public Iterable<Reservation> getFilteredReservations(AbstractFilter<Reservation> filter) {
        FilteredRepository<Integer, Reservation> filteredRepository = new FilteredRepository<>(reservationRepository, filter);
        return filteredRepository.getAll();
    }

    private void validateAvailability(Integer carId, LocalDate startDate, LocalDate endDate, Integer id) throws
            ServiceException {
        for (Reservation reservation : reservationRepository.getAll()) {
            if (reservation.getId().equals(id)) {
                continue;
            }
            if (reservation.getCarId().equals(carId)) {
                if (!(reservation.getEndDate().isBefore(startDate) || endDate.isBefore(reservation.getStartDate()))) {
                    throw new ServiceException("Car is already reserved during this period.");
                }
            }
        }
    }
}
