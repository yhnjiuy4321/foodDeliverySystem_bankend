package fcu.example.demo.service;

import fcu.example.demo.modal.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    List<Restaurant> restaurants = new ArrayList<>();

    @Autowired
    DatabaseService dbService;


    //取得所有資料
    public List<Restaurant> listAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Restaurant";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(rs.getString("Restaurant_id"));
                restaurant.setName(rs.getString("Name"));
                restaurant.setTel(rs.getString("Tel"));
                restaurant.setEmail(rs.getString("Email"));
                restaurant.setAddress(rs.getString("Address"));
                restaurant.setScore(rs.getDouble("Score"));
                restaurant.setPhoto(rs.getString("photo"));
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    //取得單一資料
    public Restaurant getRestaurant(String RestaurantID) {
        String sql = "SELECT * FROM Restaurant WHERE Restaurant_id = ?"; //這邊要與DB資料表的欄位名稱相同
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, RestaurantID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(rs.getString("Restaurant_id"));
                    restaurant.setName(rs.getString("Name"));
                    restaurant.setTel(rs.getString("Tel"));
                    restaurant.setEmail(rs.getString("Email"));
                    restaurant.setAddress(rs.getString("Address"));
                    restaurant.setScore(rs.getDouble("Score"));
                    restaurant.setPhoto(rs.getString("photo"));
                    return restaurant;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //新增資料
    public Restaurant create(Restaurant restaurant) {
        String sql = "INSERT INTO Restaurant(Restaurant_id, Name, Tel, Email, Address, Score,photo) VALUES(?, ?, ?, ?, ?, ?,?)";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, restaurant.getId());
            pstmt.setString(2, restaurant.getName());
            pstmt.setString(3, restaurant.getTel());
            pstmt.setString(4, restaurant.getEmail());
            pstmt.setString(5, restaurant.getAddress());
            pstmt.setDouble(6, restaurant.getScore());
            pstmt.setString(7, restaurant.getPhoto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    //找ID刪除
    public void deleteRestaurant(String RestaurantID) {
        String sql = "DELETE FROM Restaurant WHERE Restaurant_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, RestaurantID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //更新指定ID的資料
    public Restaurant updateRestaurant(String RestaurantID, String RestaurantName, String RestaurantTel, String RestaurantEmail, String RestaurantAddress, double RestaurantScore, String RestaurantPhoto) {
        String sql = "UPDATE Restaurant SET Name = ?, Tel = ?, Email = ?, Address = ?, Score = ? , photo = ? WHERE Restaurant_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, RestaurantName);
            pstmt.setString(2, RestaurantTel);
            pstmt.setString(3, RestaurantEmail);
            pstmt.setString(4, RestaurantAddress);
            pstmt.setDouble(5, RestaurantScore);
            pstmt.setString(6, RestaurantPhoto);
            pstmt.setString(7, RestaurantID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //查詢指定資料
    public List<Restaurant> getRestaurantBySearch(String id, String name, String tel, String email, String address) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM Restaurant WHERE Restaurant_id LIKE ? AND Name LIKE ? AND Tel LIKE ? AND Email LIKE ? AND Address LIKE ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id != null ? "%" + id + "%" : "%");
            pstmt.setString(2, name != null ? "%" + name + "%" : "%");
            pstmt.setString(3, tel != null ? "%" + tel + "%" : "%");
            pstmt.setString(4, email != null ? "%" + email + "%" : "%");
            pstmt.setString(5, address != null ? "%" + address + "%" : "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(rs.getString("Restaurant_id"));
                    restaurant.setName(rs.getString("Name"));
                    restaurant.setTel(rs.getString("Tel"));
                    restaurant.setEmail(rs.getString("Email"));
                    restaurant.setAddress(rs.getString("Address"));
                    restaurant.setScore(rs.getDouble("Score"));
                    restaurant.setPhoto(rs.getString("photo"));
                    restaurants.add(restaurant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}


