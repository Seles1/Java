package Repository;

import Domain.Car;
import Domain.Reservation;

import java.io.*;
import java.util.HashMap;

public class ReservationRepositoryBinaryFile extends FileRepository<Integer, Reservation> {
    int nextAvailableId;

    public ReservationRepositoryBinaryFile(String filename) throws Exception {
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
                this.elements = (HashMap<Integer, Reservation>) objectInputStream.readObject();
                Reservation lastReservation = null;
                for (Reservation r : this.elements.values()) {
                    if (r.getId() != null) {
                        lastReservation = r;
                    }
                }
                if (lastReservation != null) {
                    nextAvailableId = lastReservation.getId() + 1;
                }
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

    @Override
    public void add(Reservation r) throws Exception {
        r.setId(nextAvailableId);
        super.add(r);
        nextAvailableId++;
    }
}
