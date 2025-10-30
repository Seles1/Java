
import Domain.Car;
import Domain.Reservation;
import Repository.CarRepository;
import Repository.IRepository;
import Repository.ReservationRepository;
import Service.CarService;
import Service.ReservationService;
import UI.UI;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception{
        Repository.IRepository<Integer, Car> carRepository = new CarRepository();
        IRepository<Integer, Reservation> reservationRepository = new ReservationRepository();
        CarService carService = new CarService(carRepository, reservationRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, carRepository);
        UI ui = new UI(carService, reservationService);
        ui.run();
    }
}
