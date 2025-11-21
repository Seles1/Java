package Repository;

import Domain.Car;
import Exceptions.RepositoryException;

import java.io.*;

public class CarRepositoryTextFile extends FileRepository<Integer, Car> {
    protected int nextAvailableId;

    public CarRepositoryTextFile(String filename) throws Exception {
        super(filename);
    }

    @Override
    public void readFromFile() throws RepositoryException {
        nextAvailableId = 1;
        File file = new File(fileName);
        if (file.length() != 0 && file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
                String line = bufferedReader.readLine();
                while (line != null) {
                    String[] tokens = line.split(",");
                    if (tokens.length != 5) {
                        continue;
                    }
                    Integer id = Integer.parseInt(tokens[0]);
                    nextAvailableId = id;
                    String brand = tokens[1];
                    String model = tokens[2];
                    int price = Integer.parseInt(tokens[3]);
                    String color = tokens[4];
                    Car car = new Car(id, brand, model, price, color);
                    super.add(car);
                    line = bufferedReader.readLine();
                }
                nextAvailableId++;
            } catch (Exception e) {
                throw new RepositoryException("Error reading from file: " + e.getMessage());
            }
        }
    }

    public void writeToFile() throws RepositoryException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            elements.values().forEach(c -> {
                try {
                    bufferedWriter.write(c.toString());
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                };
            });
        } catch (Exception e) {
            throw new RepositoryException("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void add(Car c) throws RepositoryException {
        c.setId(nextAvailableId);
        System.out.println(c.getId());
        super.add(c);
        nextAvailableId++;
    }
}
