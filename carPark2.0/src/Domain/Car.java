package Domain;

public class Car implements Identifiable<Integer> {
    private Integer id;
    private String brand;
    private String model;
    private int price;
    private String color;

    public Car(Integer id, String brand, String model, int price, String color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.color = color;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public String getModel() {
        return model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setModel(String model) {
        this.model = model;
    }


    @Override
    public String toString() {
        return +id + "," + brand + "," + model + "," + price + "," + color;
    }
}
