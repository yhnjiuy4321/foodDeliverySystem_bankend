package fcu.example.demo.service;

import fcu.example.demo.modal.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    DatabaseService dbService;

    List<Menu> menus = new ArrayList<>();

    //取得所有資料
    public List<Menu> listAll() {
        List<Menu> menus = new ArrayList<>();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Menu, Restaurant WHERE Menu.Restaurant_id = Restaurant.Restaurant_id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getString("Dish_id"));
                menu.setName(rs.getString("Name"));
                menu.setCost(rs.getInt("Cost"));
                menu.setScore(rs.getDouble("Score"));
                menu.setF_restaurant_id(rs.getString("Restaurant.name"));
                menus.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    //查詢單一資料
    public List<Menu> getMenu(String DishID) {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM Menu,Restaurant WHERE Menu.Restaurant_id = ? AND Menu.Restaurant_id = Restaurant.Restaurant_id";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, DishID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Menu menu = new Menu();
                    menu.setId(rs.getString("Dish_id"));
                    menu.setName(rs.getString("Name"));
                    menu.setCost(rs.getInt("Cost"));
                    menu.setScore(rs.getDouble("Score"));
                    menu.setPhoto(rs.getString("photo"));
                    menu.setF_restaurant_id(rs.getString("Restaurant_id"));
                    menus.add(menu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return all  menus
        return menus;
    }
    //新增資料
    public Menu create(Menu menu) {
        String sql = "INSERT INTO Menu(Dish_id, Name, Cost, Score, Restaurant_id) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, menu.getId());
            pstmt.setString(2, menu.getName());
            pstmt.setInt(3, menu.getCost());
            pstmt.setDouble(4, menu.getScore());
            pstmt.setString(5, menu.getF_restaurant_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menu;
    }

    //找ID刪除
    public void deleteMenu(String DishID) {
        String sql = "DELETE FROM Menu WHERE Dish_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, DishID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新資料
    public void updateMenu(String DishID, String DishName, int DishCost, double DishScore, String RestaurantID, String DishPhoto) {
        String sql = "UPDATE Menu SET Name = ?, Cost = ?, Score = ?, Restaurant_id = ? ,photo = ? WHERE Dish_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, DishName);
            pstmt.setInt(2, DishCost);
            pstmt.setDouble(3, DishScore);
            pstmt.setString(4, RestaurantID);
            pstmt.setString(5, DishPhoto);
            pstmt.setString(6, DishID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Menu getMenuByRestaurantAndMenuId(String id_m) {
        String sql = "SELECT * FROM Menu WHERE Dish_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id_m);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Menu menu = new Menu();
                    menu.setId(rs.getString("Dish_id"));
                    menu.setName(rs.getString("Name"));
                    menu.setCost(rs.getInt("Cost"));
                    menu.setScore(rs.getDouble("Score"));
                    menu.setF_restaurant_id(rs.getString("Restaurant_id"));
                    menu.setPhoto(rs.getString("photo"));
                    return menu;

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Menu> gstMenuBySearch(String Restaurant_id,String id, String name) {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM Menu WHERE Restaurant_id = ? AND Dish_id LIKE ? AND Name LIKE ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Restaurant_id);
            pstmt.setString(2, id != null ? "%" + id + "%" : "%");
            pstmt.setString(3, name != null ? "%" + name + "%" : "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Menu menu = new Menu();
                    menu.setId(rs.getString("Dish_id"));
                    menu.setName(rs.getString("Name"));
                    menu.setCost(rs.getInt("Cost"));
                    menu.setScore(rs.getDouble("Score"));
                    menu.setF_restaurant_id(rs.getString("Restaurant_id"));
                    menu.setPhoto(rs.getString("photo"));
                    menus.add(menu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }
}



