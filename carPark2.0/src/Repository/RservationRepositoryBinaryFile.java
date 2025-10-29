package Repository;

import Domain.Reservation;

import java.io.*;
import java.util.HashMap;

public class RservationRepositoryBinaryFile extends FileRepository<Integer, Reservation> {
    public ReservationRepositoryBinaryFile(String filename) throws Exception {
        super(filename);
    }

    @Override
    public void readFromFile() throws Exception {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            this.elements = (HashMap<Integer, Reservation>) objectInputStream.readObject();
        } catch (Exception e) {
            throw new Exception();
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
}
