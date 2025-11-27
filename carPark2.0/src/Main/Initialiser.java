package Main;

import Domain.Car;
import Domain.Reservation;
import Repository.*;
import Service.CarService;
import Service.ReservationService;
import UI.UI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Initialiser {
    public static IRepository<Integer, Car> createCarFromSettings() throws Exception {
        try {
            InputStream is = new FileInputStream("src/settings.properties");
            Properties properties = new Properties();
            properties.load(is);
            String repoType = properties.getProperty("RepositoryType");
            IRepository<Integer, Car> carRepo;
            if (repoType.equals("memory")) {
                carRepo = new CarRepository();
            } else if (repoType.equals("text")) {
                String carPath = properties.getProperty("CarPath");
                carRepo = new CarRepositoryTextFile(carPath);
            } else if (repoType.equals("binary")) {
                String carPath = properties.getProperty("CarPath");
                carRepo = new CarRepositoryBinaryFile(carPath);
            } else if (repoType.equals("database")) {
                String carPath = properties.getProperty("CarPath");
                carRepo = new CarRepositoryDB(carPath);
            } else {
                throw new Exception("Invalid repository type");
            }
            return carRepo;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static IRepository<Integer, Reservation> createReservationFromSettings() throws Exception {
        try {
            InputStream is = new FileInputStream("src/settings.properties");
            Properties properties = new Properties();
            properties.load(is);
            String repoType = properties.getProperty("RepositoryType");
            IRepository<Integer, Reservation> reservationRepo;
            if (repoType.equals("memory")) {
                reservationRepo = new ReservationRepository();
            } else if (repoType.equals("text")) {
                String reservationPath = properties.getProperty("ReservationPath");
                reservationRepo = new ReservationRepositoryTextFile(reservationPath);
            } else if (repoType.equals("binary")) {
                String reservationPath = properties.getProperty("ReservationPath");
                reservationRepo = new ReservationRepositoryBinaryFile(reservationPath);
            } else if (repoType.equals("database")) {
                String reservationPath = properties.getProperty("ReservationPath");
                reservationRepo = new ReservationRepositoryDB(reservationPath);
            } else {
                throw new Exception("Invalid repository type");
            }
            return reservationRepo;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
//InputStream is = new FileInputStream("src/settings.properties");
//Properties properties = new Properties();
//        properties.load(is);
//String repoType = properties.getProperty("RepositoryType");
//IRepository<Integer, Car> carRepo;
//IRepository<Integer, Reservation> reservationRepo;
//        if (repoType.equals("memory")) {
//carRepo = new CarRepository();
//reservationRepo = new ReservationRepository();
//        } else if (repoType.equals("text")) {
//String carPath = properties.getProperty("CarPath");
//String reservationPath = properties.getProperty("ReservationPath");
//carRepo = new CarRepositoryTextFile(carPath);
//reservationRepo = new ReservationRepositoryTextFile(reservationPath);
//        } else if (repoType.equals("binary")) {
//String carPath = properties.getProperty("CarPath");
//String reservationPath = properties.getProperty("ReservationPath");
//carRepo = new CarRepositoryBinaryFile(carPath);
//reservationRepo = new ReservationRepositoryBinaryFile(reservationPath);
//        } else if (repoType.equals("database")) {
//String carPath = properties.getProperty("CarPath");
//String reservationPath = properties.getProperty("ReservationPath");
//carRepo = new CarRepositoryDB(carPath);
//reservationRepo = new ReservationRepositoryDB(reservationPath);
//        } else {
//                throw new Exception("Invalid repository type");
//        }
//CarService carService = new CarService(carRepo, reservationRepo);
//ReservationService reservationService = new ReservationService(reservationRepo, carRepo);