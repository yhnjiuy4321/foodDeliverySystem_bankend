package fcu.example.demo.service;

import fcu.example.demo.modal.Order_details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Order_detailsService {

    @Autowired
    DatabaseService dbService;

    List<Order_details> order_details = new ArrayList<>();

    //取得所有資料
    public List<Order_details> listAll() {
        List<Order_details> order_details = new ArrayList<>();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Order_Details";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order_details order_detail = new Order_details();
                order_detail.setF_o_id(rs.getString("Order_id"));
                order_detail.setF_o_id(rs.getString("Restaurant_id"));
                order_detail.setF_m_id(rs.getString("Dish_id"));

                order_details.add(order_detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order_details;
    }

    //取得單一資料
    public Order_details getOrder_details(String OrderID) {
        String sql = "SELECT * FROM Order_Details WHERE Order_id = ?"; //這邊要與DB資料表的欄位名稱相同
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, OrderID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order_details order_detail = new Order_details();
                    order_detail.setF_o_id(rs.getString("Order_id"));
                    order_detail.setF_o_id(rs.getString("Restaurant_id"));
                    order_detail.setF_m_id(rs.getString("Dish_id"));
                    return order_detail;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //新增資料
    public void addOrder_details(Order_details order_detail) {
        String sql = "INSERT INTO Order_Details(Order_id,Restaurant_id,Dish_id) VALUES(?,?,?)";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, order_detail.getF_o_id());
            pstmt.setString(2, order_detail.getF_r_id());
            pstmt.setString(3, order_detail.getF_m_id());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();


        }
    }

    //刪除資料
    public void deleteOrder_details(String OrderID) {
        String sql = "DELETE FROM Order_Details WHERE Order_id = ?";//這邊要與DB資料表的欄位名稱相同

        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, OrderID); //從1開始，setString是設定字串，setInt是設定整數
            pstmt.executeUpdate();//意思是執行更新
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改資料
    public void updateOrder_details(String OrderID, String RestaurantID, String DishID) {
        String sql = "UPDATE Order_Details SET Restaurant_id = ?, Dish_id = ? WHERE Order_id = ?";//這邊要與DB資料表的欄位名稱相同

        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, RestaurantID); //從1開始，setString是設定字串，setInt是設定整數
            pstmt.setString(2, DishID);
            pstmt.setString(3, OrderID);
            pstmt.executeUpdate();//意思是執行更新
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

