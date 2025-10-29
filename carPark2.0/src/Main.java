
import Repository.CarRepository;
import Repository.ReservationRepository;
import Service.CarService;
import Service.ReservationService;
import UI.UI;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception{
        CarRepository carRepository = new CarRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        CarService carService = new CarService(carRepository, reservationRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, carRepository);
        UI ui = new UI(carService, reservationService);
        ui.run();
    }
}
