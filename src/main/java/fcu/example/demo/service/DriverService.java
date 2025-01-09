package fcu.example.demo.service;
import fcu.example.demo.modal.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Service
public class DriverService {

    @Autowired
    DatabaseService dbService;

    List<Driver> drivers = new ArrayList<>();

    //取得所有資料
    public List<Driver> listAll() {
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Driver";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getString("Driver_id"));
                driver.setName(rs.getString("Name"));
                driver.setTel(rs.getString("Tel"));
                driver.setEmail(rs.getString("Email"));
                driver.setScore(rs.getDouble("Score"));
                driver.setNum_of_delivery(rs.getInt("Number_of_deliveries"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }


    //新增資料

    public Driver create(Driver driver) {
        String sql = "INSERT INTO Driver(Driver_id, Name, Tel, Email, Score, Number_of_deliveries) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driver.getId());
            pstmt.setString(2, driver.getName());
            pstmt.setString(3, driver.getTel());
            pstmt.setString(4, driver.getEmail());
            pstmt.setDouble(5, driver.getScore());
            pstmt.setInt(6, driver.getNum_of_delivery());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

}
        return driver;
    }

    //找ID刪除
    public void deleteDriver(String DriverID) {
        String sql = "DELETE FROM Driver WHERE Driver_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, DriverID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新指定ID的資料
    public Driver updateDriver(String DriverID, String DriverName, String DriverTel, String DriverEmail, double DriverScore, int DriverNum_of_delivery) {
        String sql = "UPDATE Driver SET Name = ?, Tel = ?, Email = ?, Score = ?, Number_of_deliveries = ? WHERE Driver_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, DriverName);
            pstmt.setString(2, DriverTel);
            pstmt.setString(3, DriverEmail);
            pstmt.setDouble(4, DriverScore);
            pstmt.setInt(5, DriverNum_of_delivery);
            pstmt.setString(6, DriverID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //查詢指定ID的資料
    public Driver getDriverBySearch(String DriverID) {
        String sql = "SELECT * FROM Driver WHERE Driver_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, DriverID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Driver driver = new Driver();
                    driver.setId(rs.getString("Driver_id"));
                    driver.setName(rs.getString("Name"));
                    driver.setTel(rs.getString("Tel"));
                    driver.setEmail(rs.getString("Email"));
                    driver.setScore(rs.getDouble("Score"));
                    driver.setNum_of_delivery(rs.getInt("Number_of_deliveries"));
                    return driver;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //刪除指定ID的資料
    public void delete(String id) {
        String sql = "DELETE FROM Driver WHERE Driver_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查詢指定ID的資料
    public List<Driver> getDriverBySearch(String id, String name, String tel, String email) {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM Driver WHERE Driver_id LIKE ? AND Name LIKE ? AND Tel LIKE ? AND Email LIKE ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id != null ? "%" + id + "%" : "%");
            pstmt.setString(2, name != null ? "%" + name + "%" : "%");
            pstmt.setString(3, tel != null ? "%" + tel + "%" : "%");
            pstmt.setString(4, email != null ? "%" + email + "%" : "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Driver driver = new Driver();
                    driver.setId(rs.getString("Driver_id"));
                    driver.setName(rs.getString("Name"));
                    driver.setTel(rs.getString("Tel"));
                    driver.setEmail(rs.getString("Email"));
                    driver.setScore(rs.getDouble("Score"));
                    driver.setNum_of_delivery(rs.getInt("Number_of_deliveries"));
                    drivers.add(driver);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(drivers);
        return drivers;
    }



}


