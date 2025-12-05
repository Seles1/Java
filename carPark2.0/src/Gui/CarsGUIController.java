package Gui;

import Exceptions.ServiceException;
import javafx.event.ActionEvent;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import Domain.Car;
import Domain.Reservation;
import Service.CarService;
import Service.ReservationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.imageio.IIOException;

public class CarsGUIController {
    private CarService carService;
    private ReservationService reservationService;

    public CarsGUIController(CarService carService, ReservationService reservationService) {
        this.carService = carService;
        this.reservationService = reservationService;
    }

    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableView<Reservation> reservationTableView;

    @FXML
    private TextField carTextField;
    @FXML
    private TextField reservationTextField;

    @FXML
    private Button carAddButton;
    @FXML
    private Button carModifyButton;
    @FXML
    private Button carDeleteButton;
    @FXML
    private Button carApplyButton;
    @FXML
    private Button reservationApplyButton;
    @FXML
    private Button reservationAddButton;
    @FXML
    private Button reservationModifyButton;
    @FXML
    private Button reservationDeleteButton;
    @FXML
    private Button deleteAllButton;

    @FXML
    private ChoiceBox<String> carFilterChoiceBox;
    @FXML
    private ChoiceBox<String> reservationFilterChoiceBox;

    @FXML
    private TableColumn<Car, Integer> carIdColumn;
    @FXML
    private TableColumn<Car, String> carBrandColumn;
    @FXML
    private TableColumn<Car, String> carModelColumn;
    @FXML
    private TableColumn<Car, Integer> carPriceColumn;
    @FXML
    private TableColumn<Car, String> carColorColumn;
    @FXML
    private TableColumn<Reservation, Integer> reservationIdColumn;
    @FXML
    private TableColumn<Reservation, Integer> reservationCarIdColumn;
    @FXML
    private TableColumn<Reservation, String> reservationCustomerNameColumn;
    @FXML
    private TableColumn<Reservation, LocalDate> reservationStartDateColumn;
    @FXML
    private TableColumn<Reservation, LocalDate> reservationEndDateColumn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        carColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservationCarIdColumn.setCellValueFactory(new PropertyValueFactory<>("carId"));
        reservationCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        reservationStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        reservationEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        carFilterChoiceBox.getItems().addAll("by Brand", "by Max Price", "by Customer", "by Color");
        carFilterChoiceBox.setValue("by Brand");
        reservationFilterChoiceBox.getItems().addAll("by Customer", "by Start Date", "at a Given Date", "by a Car Id");
        reservationFilterChoiceBox.setValue("by Customer");
        populateTable();
    }

    @FXML
    void deleteAllHandler(){
        List<Reservation> reservationList=reservationService.getAllReservations();
        try {
            reservationList.forEach(reservation -> {
                try {
                    reservationService.deleteReservation(reservation.getId());
                } catch (ServiceException e) {
                }
            });
        }catch (Exception e){
        }
        populateTable();
    }

    void populateTable() {
        this.carTableView.getItems().setAll(this.carService.getAllCars());
        this.reservationTableView.getItems().setAll(this.reservationService.getAllReservations());
    }

    @FXML
    void carAddButtonHandler() {
        showCarDialog(null, "Add New Car");
    }

    @FXML
    void carModifyButtonHandler() {
        Car selectedCar = carTableView.getSelectionModel().getSelectedItem();
        if (selectedCar == null) {
            showAlert("No selection", "Please select a car to modify.");
            return;
        }
        showCarDialog(selectedCar, "Modify Car");
    }
    @FXML
    void reservationAddButtonHandler(){
        showReservationDialog(null,"Add New Reservation");
    }

    @FXML
    void reservationModifyButtonHandler(){
        Reservation selectedReservation= reservationTableView.getSelectionModel().getSelectedItem();
        if(selectedReservation==null){
            showAlert("No selection","Please select a car to modify");
            return;
        }
        showReservationDialog(selectedReservation,"Modify Reservation");
    }

    private void showCarDialog(Car carToModify, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CarsAditionalWindows.fxml"));
            CarsAditionalController controller = new CarsAditionalController(carToModify);
            loader.setController(controller);
            Parent parent = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initOwner(carFilterChoiceBox.getScene().getWindow());
            dialogStage.setScene(new Scene(parent));
            dialogStage.showAndWait();

            Car result = controller.getResultCar();
            if (result != null) {
                if (title.equals("Modify Car")) {
                    carService.updateCar(result.getId(), result.getBrand(), result.getModel(), result.getPrice(), result.getColor());
                } else {
                    carService.addCar(result.getBrand(), result.getModel(), result.getPrice(), result.getColor());
                }
                populateTable();
            }
        } catch (ServiceException e) {
            showAlert("Service Error", "addition/update failed: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Could not load car dialog: " + e.getMessage());
        }
    }
    private void showReservationDialog(Reservation reservationToModify,String title){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationAditionalWindow.fxml"));
            ReservationAditionalController controller = new ReservationAditionalController(reservationToModify);
            loader.setController(controller);
            Parent parent = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initOwner(reservationFilterChoiceBox.getScene().getWindow());
            dialogStage.setScene(new Scene(parent));
            dialogStage.showAndWait();

            Reservation result = controller.getResultReservation();
            if (result != null) {
                if (title.equals("Modify Reservation")) {
                    reservationService.updateReservation(result.getId(),result.getCarId(), result.getCustomerName(),result.getStartDate(),result.getEndDate());
                } else {
                    reservationService.addReservation(result.getCarId(), result.getCustomerName(), result.getStartDate(), result.getEndDate());
                }
                populateTable();
            }
        } catch (ServiceException e) {
            showAlert("Service Error", "addition/update failed: " + e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Could not load car dialog: " + e.getMessage());
        }
    }

    @FXML
    void carDeleteButtonHandler() {
        Car selectedCar = this.carTableView.getSelectionModel().getSelectedItem();
        if (selectedCar == null) {
            showAlert("No Selection", "Please select a car to delete.");
            return;
        }
        try {
            carService.deleteCar(selectedCar.getId());
            this.populateTable();
        } catch (Exception e) {
            showAlert("Error", "Failed to delete car: " + e.getMessage());
        }
    }
    @FXML
    void reservationDeleteButtonHandler(){
        Reservation selectedReservation=this.reservationTableView.getSelectionModel().getSelectedItem();
        if(selectedReservation==null){
            showAlert("No selection","Please select a reservation to delete");
            return;
        }
        try{
            reservationService.deleteReservation(selectedReservation.getId());
            this.populateTable();
        }catch (Exception e){
            showAlert("Error","Failed to delete reservation: "+e.getMessage());
        }
    }

    @FXML
    void CarApplyButtonHandler() {
        String selectedFilter = carFilterChoiceBox.getValue();
        String filterText = carTextField.getText().trim();
        if (selectedFilter == null) {
            showAlert("Invalid Input", "Please select a filter and enter a value");
        }
        if (filterText.isEmpty()) {
            populateTable();
        } else {
            try {
                switch (selectedFilter) {
                    case "by Brand":
                        Iterable<Car> cars = carService.getFilteredCars(car -> car.getBrand().equals(filterText));
                        List<Car> carList = new ArrayList<>();
                        cars.forEach(carList::add);
                        carTableView.getItems().setAll(carList);
                        break;
                    case "by Max Price":
                        Iterable<Car> cars2 = carService.getFilteredCars(car -> car.getPrice() <= Integer.parseInt(filterText));
                        List<Car> carList2 = new ArrayList<>();
                        cars2.forEach(carList2::add);
                        carTableView.getItems().setAll(carList2);
                        break;
                    case "by Customer":
                        carTableView.getItems().setAll(carService.getCarsRentedByCustomer(filterText));
                        break;
                    case "by Color":
                        carTableView.getItems().setAll(carService.getCarsOfAGivenColor(filterText));
                        break;
                    case null:
                        break;
                    default:
                        showAlert("Error", "Unknown");
                }
            } catch (Exception e) {
                showAlert("Error", "Filter failed: " + e.getMessage());
            }
        }
    }

    @FXML
    void reservationApplyButtonHandler(){
        String selectedFilter = reservationFilterChoiceBox.getValue();
        String filterText = reservationTextField.getText().trim();
        if (selectedFilter == null) {
            showAlert("Invalid Input", "Please select a filter and enter a value");
        }
        if (filterText.isEmpty()) {
            populateTable();
        } else {
            try {
                switch (selectedFilter) {
                    case "by Customer":
                        Iterable<Reservation> reservations = reservationService.getFilteredReservations(reservation -> reservation.getCustomerName().equals(filterText));
                        List<Reservation> reservationList = new ArrayList<>();
                        reservations.forEach(reservationList::add);
                        reservationTableView.getItems().setAll(reservationList);
                        break;
                    case "by Start Date":
                        Iterable<Reservation> reservations2 = reservationService.getFilteredReservations(reservation -> !reservation.getStartDate().isBefore(LocalDate.parse(filterText)));
                        List<Reservation> reservationList2 = new ArrayList<>();
                        reservations2.forEach(reservationList2::add);
                        reservationTableView.getItems().setAll(reservationList2);
                        break;
                    case "at a Given Date":
                        reservationTableView.getItems().setAll(reservationService.getActiveReservationsAtAGivenDate(LocalDate.parse(filterText)));
                        break;
                    case "by a Car Id":
                        List<String> text=new ArrayList<>();
                        text=reservationService.getCustomerNameByCarId(Integer.parseInt(filterText));
                        showAlert("Report",text.toString());
                        break;
                    case null:
                        break;
                    default:
                        showAlert("Error", "Unknown");
                }
            } catch (Exception e) {
                showAlert("Error", "Filter failed: " + e.getMessage());
            }
        }
    }
    @FXML
    void carExitButtonHandler(){
        Stage stage=(Stage) carTextField.getScene().getWindow();
        stage.close();
    }
    @FXML
    void reservationExitButtonHandler(){
        Stage stage=(Stage) reservationTextField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}