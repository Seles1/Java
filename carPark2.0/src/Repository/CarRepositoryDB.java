package Repository;

import Domain.Car;
import Exceptions.RepositoryException;
import Exceptions.ServiceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class CarRepositoryDB implements IRepository<Integer, Car> {
    private String URL;
    private Connection conn = null;

    public CarRepositoryDB(String URL) {
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
    public void add(Car element) throws RepositoryException {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Cars (brand, model, price, color) VALUES (?,?,?,?)");
            st.setString(1, element.getBrand());
            st.setString(2, element.getModel());
            st.setString(4, element.getColor());
            st.setInt(3, element.getPrice());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public Optional<Car> delete(Integer id) throws RepositoryException {
        System.out.println("D");
        openConnection();
        Car car = null;
        try {
            PreparedStatement rt = conn.prepareStatement("SELECT * FROM Cars WHERE Cars.id=?");
            rt.setInt(1, id);
            ResultSet rs = rt.executeQuery();
            if (rs.next()) {
                car = new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("price"),
                        rs.getString("color")
                );
            }
            rt.close();
            if (car != null) {
                PreparedStatement st = conn.prepareStatement("DELETE FROM Cars WHERE Cars.id=?");
                st.setInt(1, id);
                st.executeUpdate();
                st.close();
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
        }

        return Optional.ofNullable(car);
    }

    @Override
    public void modify(Car element) throws RepositoryException {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE Cars SET brand=?, model=?, price=?, color=? WHERE id=?");
            st.setString(1, element.getBrand());
            st.setString(2, element.getModel());
            st.setInt(3, element.getPrice());
            st.setString(4, element.getColor());
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
    public Optional<Car> findById(Integer id) throws RepositoryException {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * from Cars WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int Id = rs.getInt("id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int price = rs.getInt("price");
                String color = rs.getString("color");
                st.close();
                Car car = new Car(Id, brand, model, price, color);
                return Optional.of(car);
            } else {
                st.close();
                throw new RepositoryException("Car not found");
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        } finally {
            closeConnection();
        }
    }


    public Iterable<Car> getAll() {
        openConnection();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * from Cars");
            ResultSet rs = st.executeQuery();
            ArrayList<Car> list = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int price = rs.getInt("price");
                String color = rs.getString("color");
                Car car = new Car(id, brand, model, price, color);
                list.add(car);
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
