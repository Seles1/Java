
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
import java.time.LocalDate;
import java.util.Properties;

public UI createFromClass(UI ui){
    InputStream is = new FileInputStream("src/settings.properties");
        Properties properties = new Properties();
        properties.load(is);
        String repoType = properties.getProperty("RepositoryType");
        IRepository<Integer, Car> carRepo;
        IRepository<Integer, Reservation> reservationRepo;
        if (repoType.equals("memory")) {
            carRepo = new CarRepository();
            reservationRepo = new ReservationRepository();
        } else if (repoType.equals("text")) {
            String carPath = properties.getProperty("CarPath");
            String reservationPath = properties.getProperty("ReservationPath");
            carRepo = new CarRepositoryTextFile(carPath);
            reservationRepo = new ReservationRepositoryTextFile(reservationPath);
        } else if (repoType.equals("binary")) {
            String carPath = properties.getProperty("CarPath");
            String reservationPath = properties.getProperty("ReservationPath");
            carRepo = new CarRepositoryBinaryFile(carPath);
            reservationRepo = new ReservationRepositoryBinaryFile(reservationPath);
        } else if (repoType.equals("database")) {
            String carPath = properties.getProperty("CarPath");
            String reservationPath = properties.getProperty("ReservationPath");
            carRepo = new CarRepositoryDB(carPath);
            reservationRepo = new ReservationRepositoryDB(reservationPath);
        } else {
            throw new Exception("Invalid repository type");
        }
        CarService carService = new CarService(carRepo, reservationRepo);
        ReservationService reservationService = new ReservationService(reservationRepo, carRepo);
        return new UI(carService, reservationService);
}


public class Main {
    public static void main(String[] args) throws Exception{
        UI ui = UI.createFromSettings();
        ui.run();
    }
}
