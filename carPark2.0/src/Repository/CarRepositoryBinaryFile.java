package Repository;

import Domain.Car;

import java.io.*;
import java.util.HashMap;

public class CarRepositoryBinaryFile extends FileRepository<Integer, Car> {
    int nextAvailableId;

    public CarRepositoryBinaryFile(String filename) throws Exception {
        super(filename);
    }

    @Override
    public void readFromFile() throws Exception {
        nextAvailableId = 1;
        this.elements = new HashMap<>();
        File file = new File(fileName);
        if (file.length() != 0 && file.exists()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
                this.elements = (HashMap<Integer, Car>) objectInputStream.readObject();
                Car lastCar = null;
                for (Car c : this.elements.values()) {
                    if (c.getId() != null) {
                        lastCar = c;
                    }
                }
                if (lastCar != null) {
                    nextAvailableId = lastCar.getId() + 1;
                }
            } catch (Exception e) {
                throw new Exception("Error writing/reading binary file: " + e.getMessage(), e);
            }
        }
    }

    public void writeToFile() throws Exception {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(this.elements);
        } catch (Exception e) {
            throw new Exception("Error writing/reading binary file: " + e.getMessage(), e);
        }
    }

    @Override
    public void add(Car c) throws Exception {
        c.setId(nextAvailableId);
        super.add(c);
        nextAvailableId++;
    }
}
