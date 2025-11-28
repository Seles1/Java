package Gui;

import Domain.Car;
import Domain.Reservation;
import Main.Initialiser;
import Repository.CarRepository;
import Repository.IRepository;
import Service.CarService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUI extends Application {
    private CarService carService;
    private ListView<Car> carsListView;
    private ObservableList<Car> carsObservableList;
    private Button addButton, deleteButton, updateButton;
    private TextField idTextField, brandTextField, modelTextField, priceTextField, colorTextField;

    public void init() throws Exception {
        IRepository<Integer, Car> carRepository = Initialiser.createCarFromSettings();
        IRepository<Integer, Reservation> reservationRepository = Initialiser.createReservationFromSettings();
        carService = new CarService(carRepository, reservationRepository);
    }

    public void start(Stage stage) throws Exception {
        HBox mainLayout = new HBox();

        carsListView = new ListView<>();
        carsObservableList = FXCollections.observableArrayList(carService.getAllCars());
        carsListView.setItems(carsObservableList);
        VBox leftLayout = new VBox();
        leftLayout.getChildren().add(carsListView);
        mainLayout.getChildren().add(leftLayout);

        GridPane rightLayout = new GridPane();
        idTextField = new TextField();
        Label brandLabel = new Label("brand");
        brandTextField = new TextField();
        Label modelLabel = new Label("model");
        modelTextField = new TextField();
        Label priceLabel = new Label("price");
        priceTextField = new TextField();
        Label colorLabel = new Label("color");
        colorTextField = new TextField();
        rightLayout.add(brandLabel, 0, 1);
        rightLayout.add(brandTextField, 1, 1);
        rightLayout.add(modelLabel, 0, 2);
        rightLayout.add(modelTextField, 1, 2);
        rightLayout.add(priceLabel, 0, 3);
        rightLayout.add(priceTextField, 1, 3);
        rightLayout.add(colorLabel, 0, 4);
        rightLayout.add(colorTextField, 1, 4);

        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        updateButton = new Button("Edit");
        rightLayout.add(addButton, 1, 5);
        rightLayout.add(deleteButton, 1, 6);
        rightLayout.add(updateButton, 0, 6);
        mainLayout.getChildren().add(rightLayout);

        this.addButtonHandler();
        this.deleteButtonHandler();
        this.updateButtonHandler();
        this.carsListHandler();


        Scene scene = new Scene(mainLayout, 500, 300);
        stage.setScene(scene);
        stage.setTitle("Car Menu");
        stage.show();

    }

    private void addButtonHandler() {
        addButton.setOnMouseClicked(e -> {
            String brand = brandTextField.getText();
            String model = modelTextField.getText();
            int price;
            try {
                price = Integer.parseInt(priceTextField.getText());
            } catch (NumberFormatException e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price must be an integer");
                alert.showAndWait();
                return;
            }
            String color = colorTextField.getText();
            try {
                carService.addCar(brand, model, price, color);
                carsObservableList = FXCollections.observableArrayList(carService.getAllCars());
                carsListView.setItems(carsObservableList);
                clearTextFields();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "One of the fields is wrong");
                alert.showAndWait();
            }
        });
    }

    private void deleteButtonHandler() {
        deleteButton.setOnMouseClicked(e -> {
            try {
                Integer id = carsListView.getSelectionModel().selectedItemProperty().get().getId();
                carService.deleteCar(id);
                carsObservableList = FXCollections.observableArrayList(carService.getAllCars());
                carsListView.setItems(carsObservableList);
                clearTextFields();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a car from the list");
                alert.showAndWait();
            }
        });
    }

    private void updateButtonHandler() {
        updateButton.setOnMouseClicked(e -> {
            Integer id;
            int price;
            try {
                id = carsListView.getSelectionModel().selectedItemProperty().get().getId();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a car from the list");
                alert.showAndWait();
                return;
            }
            String brand = brandTextField.getText();
            String model = modelTextField.getText();
            try {
                price = Integer.parseInt(priceTextField.getText());
            } catch (NumberFormatException e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price must be an integer");
                alert.showAndWait();
                return;
            }
            String color = colorTextField.getText();
            try {
                carService.updateCar(id, brand, model, price, color);
                carsObservableList = FXCollections.observableArrayList(carService.getAllCars());
                carsListView.setItems(carsObservableList);
                clearTextFields();
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e1.getMessage());
                alert.showAndWait();
            }
        });
    }

    private void clearTextFields() {
        idTextField.clear();
        brandTextField.clear();
        modelTextField.clear();
        priceTextField.clear();
        colorTextField.clear();
    }

    private void carsListHandler() {
        carsListView.setOnMouseClicked(e -> {
            Car car = carsListView.getSelectionModel().getSelectedItem();
            if (car != null) {
                idTextField.setText(car.getId().toString());
                brandTextField.setText(car.getBrand());
                modelTextField.setText(car.getModel());
                priceTextField.setText(String.valueOf(car.getPrice()));
                colorTextField.setText(car.getColor());
            }
        });
    }
}
