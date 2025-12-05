package Gui;

import java.net.URL;
import java.util.ResourceBundle;

import Domain.Car;
import Service.CarService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CarsAditionalController {
    private Car car;
    private Car resultCar;

    public CarsAditionalController(Car car) {
        this.car = car;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField carBrandTextBox;

    @FXML
    private TextField carColorTextBox;

    @FXML
    private Button carConfirmButton;

    @FXML
    private Button carExitButton;

    @FXML
    private TextField carModelTextBox;

    @FXML
    private TextField carPriceTextBox;

    @FXML
    void initialize() {
        if (car != null) {
            carBrandTextBox.setText(car.getBrand());
            carModelTextBox.setText(car.getModel());
            carPriceTextBox.setText(String.valueOf(car.getPrice()));
            carColorTextBox.setText(car.getColor());
        }
    }

    public Car getResultCar() {
        return resultCar;
    }

    @FXML
    private void carConfirmHandle() {
        try {
            String brand = carBrandTextBox.getText();
            String model = carModelTextBox.getText();
            int price = Integer.parseInt(carPriceTextBox.getText());
            String color = carColorTextBox.getText();
            if (car == null) {
                resultCar = new Car(null, brand, model, price, color);
            } else {
                car.setBrand(brand);
                car.setModel(model);
                car.setPrice(price);
                car.setColor(color);
                resultCar = car;
            }
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert("Error", "Price must be an integer");
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    @FXML
    private void carExitHandle(){
        closeWindow();
    }

    private void closeWindow(){
        Stage stage= (Stage) carBrandTextBox.getScene().getWindow();
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
