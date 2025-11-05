
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
        UI ui = UI.createFromSettings();
        ui.run();
    }
}
