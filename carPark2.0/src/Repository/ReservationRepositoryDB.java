package Repository;

import Domain.Car;
import Domain.Reservation;
import Exceptions.RepositoryException;
import Exceptions.ServiceException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class ReservationRepositoryDB implements IRepository<Integer, Reservation> {
    private String URL;
    private Connection conn = null;

    public ReservationRepositoryDB(String URL) {
        this.URL = URL;
    }

    private void openConnection() {
        try {
            if (conn == null || conn.isClosed())
                conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void add(Reservation element) throws RepositoryException {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Reservations (carID,customerName,startDate,endDate) VALUES (?,?,?,?)");
            st.setInt(1, element.getCarId());
            st.setString(2, element.getCustomerName());
            st.setString(3, element.getStartDate().toString());
            st.setString(4, element.getEndDate().toString());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public Optional<Reservation> delete(Integer id) throws RepositoryException {
        openConnection();
        Reservation reservation=null;
        try {
            PreparedStatement rt=conn.prepareStatement("SELECT * FROM Reservations WHERE Reservations.id=?");
            rt.setInt(1, id);
            ResultSet rs = rt.executeQuery();
            if (rs.next()) {
                reservation  = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("carId"),
                        rs.getString("customerName"),
                        LocalDate.parse(rs.getString("startDate")),
                        LocalDate.parse(rs.getString("endDate"))
                );
            }
            rt.close();
            PreparedStatement st = conn.prepareStatement("DELETE FROM Reservations WHERE Reservations.id=?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
            return Optional.of(reservation);
        }
    }

    @Override
    public void modify(Reservation element) throws RepositoryException {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE Reservations SET carId=?, customerName=?, startDate=?, endDate=? WHERE id=?");
            st.setInt(1, element.getCarId());
            st.setString(2, element.getCustomerName());
            st.setString(3, element.getStartDate().toString());
            st.setString(4, element.getEndDate().toString());
            st.setInt(5, element.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public Optional<Reservation> findById(Integer id) throws RepositoryException {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * from Reservations WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int Id = rs.getInt("id");
                int carId = rs.getInt("carId");
                String customerName = rs.getString("customerName");
                LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
                LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
                st.close();
                Reservation reservation = new Reservation(Id, carId, customerName, startDate, endDate);
                return Optional.of(reservation);
            } else {
                st.close();
                throw new RepositoryException("Reservation not found");
            }

        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
        }
    }


    public Iterable<Reservation> getAll() {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * from Reservations");
            ResultSet rs = st.executeQuery();
            ArrayList<Reservation> list = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer carId = rs.getInt("carId");
                String customerName = rs.getString("customerName");
                LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
                LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
                Reservation reservation = new Reservation(id, carId, customerName, startDate, endDate);
                list.add(reservation);
            }
            st.close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
