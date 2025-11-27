package Main;

import Domain.Car;
import Domain.Reservation;
import Repository.CarRepository;
import Repository.IRepository;
import Repository.ReservationRepository;
import Service.CarService;
import Service.ReservationService;
import UI.UI;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import Repository.*;

public class Main {
    public static void main(String[] args) throws Exception {
        IRepository<Integer,Car> carRepository=Initialiser.createCarFromSettings();
        IRepository<Integer,Reservation> reservationRepository=Initialiser.createReservationFromSettings();
        CarService carService=new CarService(carRepository,reservationRepository);
        ReservationService reservationService=new ReservationService(reservationRepository,carRepository);
        UI ui=new UI(carService,reservationService);
        ui.run();
    }
}
