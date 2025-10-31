package Repository;

import Domain.Car;

import java.io.*;
import java.util.HashMap;

public class CarRepositoryBinaryFile extends FileRepository<Integer, Car> {
    public CarRepositoryBinaryFile(String filename) throws Exception {
        super(filename);
    }

    @Override
    public void readFromFile() throws Exception {
        File file = new File(fileName);
        if (file.length() != 0 && file.exists()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
                this.elements = (HashMap<Integer, Car>) objectInputStream.readObject();
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }

    public void writeToFile() throws Exception {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            objectOutputStream.writeObject(this.elements);
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
