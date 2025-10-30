package Repository;

import Domain.Reservation;

import java.io.*;
import java.time.LocalDate;

public class ReservationRepositoryTextFile extends FileRepository<Integer, Reservation> {
    protected int nextAvailableId = 1;

    ReservationRepositoryTextFile(String filename) throws Exception {
        super(filename);
    }

    @Override
    public void readFromFile() throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                if(tokens.length != 5) {
                    continue;
                }
                Integer id = Integer.parseInt(tokens[0]);
                nextAvailableId = id;
                Integer carId = Integer.parseInt(tokens[1]);
                String customerName = tokens[2];
                LocalDate startDate = LocalDate.parse(tokens[3]);
                LocalDate endDate = LocalDate.parse(tokens[4]);
                Reservation reservation = new Reservation(id, carId, customerName, startDate, endDate);
                super.add(reservation);
                line = bufferedReader.readLine();
            }
            nextAvailableId++;
        } catch (Exception e) {
            throw new Exception("Error reading from file");
        }
    }

    public void writeToFile() throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Reservation r : elements.values()) {
                bufferedWriter.write(r.toString());
                bufferedWriter.newLine();
            }
        } catch (Exception e) {
            throw new Exception("Error writing to file");
        }
    }

    @Override
    public void add(Reservation r) throws Exception {
        r.setId(nextAvailableId);
        super.add(r);
        nextAvailableId++;
    }
}
