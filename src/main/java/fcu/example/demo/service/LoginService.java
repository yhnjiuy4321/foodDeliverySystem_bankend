package fcu.example.demo.service;

import fcu.example.demo.modal.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginService {


    private static final String SECRET_KEY = "yourSecretKey"; // 請替換成自己的密鑰
    private static final long EXPIRATION_TIME = 3600000; // 1 小時（以毫秒為單位）


    @Autowired
    DatabaseService dbService;

    List<Manager> managers = new ArrayList<>();

    public Manager checkLogin(String account, String password) {
        Manager manager = new Manager();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Manager WHERE Account = ? AND Password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                manager.setId(rs.getString("Emp_id"));
                manager.setName(rs.getString("Name"));
                manager.setTel(rs.getString("Tel"));
                manager.setEmail(rs.getString("Email"));
                manager.setAccount(rs.getString("Account"));
                manager.setPassword(rs.getString("Password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }


    //取得所有資料
    public List<Manager> listAll() {
        List<Manager> managers = new ArrayList<>();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Manager";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Manager manager = new Manager();
                manager.setId(rs.getString("Emp_id"));
                manager.setName(rs.getString("Name"));
                manager.setTel(rs.getString("Tel"));
                manager.setEmail(rs.getString("Email"));
                manager.setAccount(rs.getString("Account"));
                manager.setPassword(rs.getString("Password"));
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }
}
