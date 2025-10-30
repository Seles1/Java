package Repository;

import Domain.Car;

import java.io.*;

public class CarRepositoryTextFile extends FileRepository<Integer, Car> {
    protected int nextAvailableId = 1;

    CarRepositoryTextFile(String filename) throws Exception {
        super(filename);
    }

    @Override
    public void readFromFile() throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 5) {
                    Integer id = Integer.parseInt(tokens[0]);
                    nextAvailableId = id;
                    String brand = tokens[1];
                    String model = tokens[2];
                    int price = Integer.parseInt(tokens[3]);
                    String color = tokens[4];
                    Car car = new Car(id, brand, model, price, color);
                    super.add(car);
                }
                line = bufferedReader.readLine();
            }
            nextAvailableId++;
        } catch (Exception e) {
            throw new Exception("Error reading from file");
        }
    }

    public void writeToFile() throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Car c : elements.values()) {
                bufferedWriter.write(c.toString() + '\n');
            }
        } catch (Exception e) {
            throw new Exception("Error writing to file");
        }
    }

    @Override
    public void add(Car c) throws Exception {
        c.setId(nextAvailableId);
        super.add(c);
        nextAvailableId++;
    }
}
