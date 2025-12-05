package Gui;

import Main.Initialiser;
import Domain.Car;
import Domain.Reservation;
import Repository.IRepository;
import Service.CarService;
import Service.ReservationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CarsGUI.fxml"));

        IRepository<Integer,Car> carRepo= Initialiser.createCarFromSettings();
        IRepository<Integer,Reservation> reservationRepo= Initialiser.createReservationFromSettings();
        CarService carService= new CarService(carRepo,reservationRepo);
        ReservationService reservationService= new ReservationService(reservationRepo,carRepo);

        CarsGUIController controller = new CarsGUIController(carService,reservationService);
        loader.setController(controller);
        Scene scene=new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Car Shop");
        stage.setMinHeight(200);
        stage.setMinWidth(200);
        stage.show();
    }
}
