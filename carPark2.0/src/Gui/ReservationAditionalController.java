package Gui;

import java.net.URL;
import java.text.DateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Domain.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReservationAditionalController {
    private Reservation reservation;
    private Reservation resultReservation;

    public ReservationAditionalController(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getResultReservation() {
        return resultReservation;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField reservationCarIdTextBox;

    @FXML
    private Button reservationConfirmButton;

    @FXML
    private TextField reservationCustomerNameTextBox;

    @FXML
    private TextField reservationEndDateTextBox;

    @FXML
    private Button reservationExitButton;

    @FXML
    private TextField reservationStartDateTextBox;

    @FXML
    void reservationConfirmHandle() {
        try {
            Integer carId = Integer.parseInt(reservationCarIdTextBox.getText());
            String customerName = reservationCustomerNameTextBox.getText();
            LocalDate startDate = LocalDate.parse(reservationStartDateTextBox.getText());
            LocalDate endDate = LocalDate.parse(reservationEndDateTextBox.getText());
            if (reservation == null) {
                resultReservation = new Reservation(null, carId, customerName, startDate, endDate);
            } else {
                reservation.setCarId(carId);
                reservation.setCustomerName(customerName);
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                resultReservation = reservation;
            }
            closeWindow();
        } catch (DateTimeException e) {
            showAlert("Error", "Dates must be yyyy-MM-dd");
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    void reservationExitHandle(ActionEvent event) {
        closeWindow();
    }

    @FXML
    void initialize() {
        if (reservation != null) {
            reservationCarIdTextBox.setText(String.valueOf(reservation.getCarId()));
            reservationCustomerNameTextBox.setText(reservation.getCustomerName());
            reservationStartDateTextBox.setText(reservation.getStartDate().toString());
            reservationEndDateTextBox.setText(reservation.getEndDate().toString());
        }
    }


    private void closeWindow() {
        Stage stage = (Stage) reservationCarIdTextBox.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
