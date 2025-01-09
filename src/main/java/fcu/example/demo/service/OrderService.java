package fcu.example.demo.service;

import fcu.example.demo.modal.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    DatabaseService dbService;

    List<Order> orderServices = new ArrayList<>();


    //取得所有資料
    public List<Order> listAll() {
        String sql = "SELECT `Order`.*, " +
                "Restaurant.Name AS RestaurantName, " +
                "Driver.Name AS DriverName, " +
                "Customer.Name AS CustomerName, " +
                "GROUP_CONCAT(Menu.Name SEPARATOR ', ') AS DishNames, " +
                "SUM(Menu.Cost) AS TotalDishCost " +
                "FROM `Order` " +
                "JOIN Restaurant ON `Order`.Restaurant_id = Restaurant.Restaurant_id " +
                "JOIN Driver ON `Order`.Driver_id = Driver.Driver_id " +
                "JOIN Customer ON `Order`.Customer_id = Customer.Customer_id " +
                "JOIN Order_Details ON `Order`.Order_id = Order_Details.Order_id " +
                "JOIN Menu ON Order_Details.Dish_id = Menu.Dish_id " +
                "GROUP BY `Order`.Order_id";

        List<Order> orderServices = new ArrayList<>();

        try (Connection connection = dbService.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getString("Order_id"));
                order.setTime(rs.getString("Order_date"));
                order.setTotalmoney(rs.getInt("TotalDishCost")); // 設置總菜品成本
                order.setStatus(rs.getString("Order_status"));
                order.setF_restaurant_id(rs.getString("RestaurantName"));
                order.setF_driver_id(rs.getString("DriverName"));
                order.setF_customer_id(rs.getString("CustomerName"));
                order.setF_dish_id(rs.getString("DishNames")); // 合併的菜品名稱
                orderServices.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderServices;
    }


    //取得單一資料
    public Order getOrder(String OrderID) {
        String sql = "SELECT `Order`.*, " +
                "Restaurant.Name AS RestaurantName, " +
                "Driver.Name AS DriverName, " +
                "Customer.Name AS CustomerName, " +
                "GROUP_CONCAT(Menu.Name SEPARATOR ', ') AS DishNames, " +
                "SUM(Menu.Cost) AS TotalDishCost " +
                "FROM `Order` " +
                "JOIN Restaurant ON `Order`.Restaurant_id = Restaurant.Restaurant_id " +
                "JOIN Driver ON `Order`.Driver_id = Driver.Driver_id " +
                "JOIN Customer ON `Order`.Customer_id = Customer.Customer_id " +
                "JOIN Order_Details ON `Order`.Order_id = Order_Details.Order_id " +
                "JOIN Menu ON Order_Details.Dish_id = Menu.Dish_id " +
                "WHERE `Order`.Order_id = ? " +
                "GROUP BY `Order`.Order_id";

        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, OrderID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // 注意: 只需查詢一筆資料時使用 `if`
                    Order order = new Order();
                    order.setId(rs.getString("Order_id"));
                    order.setTime(rs.getString("Order_date"));
                    order.setTotalmoney(rs.getInt("TotalDishCost")); // 設置總菜品成本
                    order.setStatus(rs.getString("Order_status"));
                    order.setF_restaurant_id(rs.getString("RestaurantName"));
                    order.setF_driver_id(rs.getString("DriverName"));
                    order.setF_customer_id(rs.getString("CustomerName"));
                    order.setF_dish_id(rs.getString("DishNames")); // 合併的菜品名稱
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    //刪除資料
    public void deleteOrder(String OrderID) {
        String sql = "DELETE FROM `Order` WHERE Order_id = ?";//這邊要與DB資料表的欄位名稱相同

        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, OrderID); //從1開始，setString是設定字串，setInt是設定整數
            pstmt.executeUpdate();//意思是執行更新
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //新增資料
    public void addOrder(Order order) {
        String sql = "INSERT INTO `Order`(Order_id, Order_date, Total_money, Order_status, Restaurant_id, Driver_id, Customer_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order.getId());
            pstmt.setString(2, order.getTime());
            pstmt.setInt(3, order.getTotalmoney());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getF_restaurant_id());
            pstmt.setString(6, order.getF_driver_id());
            pstmt.setString(7, order.getF_customer_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //更新資料
    public void updateOrder(String OrderID, String OrderTime, int TotalMoney, String OrderStatus, String ResID, String DiverID, String CustomerID) {

        String sql = "UPDATE `Order` SET Order_date = ?, Total_money = ?, Order_status = ?, Restaurant_id = ?, Driver_id = ?, Customer_id = ? WHERE Order_id = ?";//這邊要與DB資料表的欄位名稱相同

        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, OrderTime); //從1開始，setString是設定字串，setInt是設定整數
            pstmt.setInt(2, TotalMoney);
            pstmt.setString(3, OrderStatus);
            pstmt.setString(4, ResID);
            pstmt.setString(5, DiverID);
            pstmt.setString(6, CustomerID);
            pstmt.setString(7, OrderID);
            pstmt.executeUpdate();//意思是執行更新
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //搜尋
    public List<Order> getOrderBySearch(String id, String time, String totalmoney, String status, String restaurant_id, String driver_id, String customer_id) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT `Order`.*, " +
                "Restaurant.Name AS RestaurantName, " +
                "Driver.Name AS DriverName, " +
                "Customer.Name AS CustomerName, " +
                "GROUP_CONCAT(Menu.Name SEPARATOR ', ') AS DishNames, " +
                "SUM(Menu.Cost) AS TotalDishCost " +
                "FROM `Order` " +
                "JOIN Restaurant ON `Order`.Restaurant_id = Restaurant.Restaurant_id " +
                "JOIN Driver ON `Order`.Driver_id = Driver.Driver_id " +
                "JOIN Customer ON `Order`.Customer_id = Customer.Customer_id " +
                "JOIN Order_Details ON `Order`.Order_id = Order_Details.Order_id " +
                "JOIN Menu ON Order_Details.Dish_id = Menu.Dish_id " +
                "WHERE `Order`.Order_id LIKE ? " +
                "AND `Order`.Order_date LIKE ? " +
                "AND `Order`.Total_money LIKE ? " +
                "AND `Order`.Order_status LIKE ? " +
                "AND `Order`.Restaurant_id LIKE ? " +
                "AND `Order`.Driver_id LIKE ? " +
                "AND `Order`.Customer_id LIKE ? " +
                "GROUP BY `Order`.Order_id";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id != null ? "%" + id + "%" : "%");
            pstmt.setString(2, time != null ? "%" + time + "%" : "%");
            pstmt.setString(3, totalmoney != null ? "%" + totalmoney + "%" : "%");
            pstmt.setString(4, status != null ? "%" + status + "%" : "%");
            pstmt.setString(5, restaurant_id != null ? "%" + restaurant_id + "%" : "%");
            pstmt.setString(6, driver_id != null ? "%" + driver_id + "%" : "%");
            pstmt.setString(7, customer_id != null ? "%" + customer_id + "%" : "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getString("Order_id"));
                    order.setTime(rs.getString("Order_date"));
                    order.setTotalmoney(rs.getInt("TotalDishCost")); // 設置總菜品成本
                    order.setStatus(rs.getString("Order_status"));
                    order.setF_restaurant_id(rs.getString("RestaurantName"));
                    order.setF_driver_id(rs.getString("DriverName"));
                    order.setF_customer_id(rs.getString("CustomerName"));
                    order.setF_dish_id(rs.getString("DishNames")); // 合併的菜品名稱
                    orders.add(order);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}