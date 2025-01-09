package fcu.example.demo.service;

import fcu.example.demo.modal.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {


    List<Customer> customers = new ArrayList<>();

    @Autowired
    DatabaseService dbService;

    //取得所有資料
    public List<Customer> listAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = dbService.connect()) {
            String sql = "SELECT * FROM Customer";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("Customer_id"));
                customer.setName(rs.getString("Name"));
                customer.setTel(rs.getString("Tel"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //取得單一資料
    public Customer getCustomer(String CustomerID) {
        String sql = "SELECT * FROM Customer WHERE Customer_id = ?"; //這邊要與DB資料表的欄位名稱相同
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CustomerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getString("Customer_id"));
                    customer.setName(rs.getString("Name"));
                    customer.setTel(rs.getString("Tel"));
                    customer.setEmail(rs.getString("Email"));
                    customer.setAddress(rs.getString("Address"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //更新資料
    public void updateCustomer(String CustomerID, String CustomerName, String CustomerTel, String CustomerEmail, String CustomerAddress) {
        String sql = "UPDATE Customer SET Name = ?, Tel = ?, Email = ?, Address = ? WHERE Customer_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CustomerName);
            pstmt.setString(2, CustomerTel);
            pstmt.setString(3, CustomerEmail);
            pstmt.setString(4, CustomerAddress);
            pstmt.setString(5, CustomerID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //在customers中新增
    public void create(Customer customer) {
        //設置ID並加入
        customers.add(customer);//加入，但是沒有存入資料庫，所以要再寫一個方法，將資料存入資料庫，這樣才能真正的新增

        //將資料存入資料庫
        addCustomer(customer.getId(), customer.getName(), customer.getTel(), customer.getEmail(), customer.getAddress());
    }

    //將資料存入資料庫
    public void addCustomer(String CustomerID, String CustomerName, String CustomerTel, String CustomerEmail, String CustomerAddress) {
        String sql = "INSERT INTO Customer (Customer_id, Name, Tel, Email, Address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CustomerID);
            pstmt.setString(2, CustomerName);
            pstmt.setString(3, CustomerTel);
            pstmt.setString(4, CustomerEmail);
            pstmt.setString(5, CustomerAddress);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //刪除資料
    public void deleteCustomer(String id) {
        //直接找尋ID並刪除資料庫的資料
        deleteCustomerFromDB(id);
        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                customers.remove(c);
                return;
            }
        }
    }

    //刪除資料庫的資料
    public void deleteCustomerFromDB(String id) {
        //直接找尋ID並刪除資料庫的資料
        String sql = "DELETE FROM Customer WHERE Customer_id = ?";
        try (Connection conn = dbService.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查詢指定資料
    public List<Customer> getCustomerBySearch(String id, String name, String tel, String email, String address) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE Customer_id LIKE ? AND Name LIKE ? AND Tel LIKE ? AND Email LIKE ? AND Address LIKE ?";

        try (Connection conn = dbService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 使用 "%" + parameter + "%" 來允許部分匹配
            pstmt.setString(1, id != null ? "%" + id + "%" : "%");
            pstmt.setString(2, name != null ? "%" + name + "%" : "%");
            pstmt.setString(3, tel != null ? "%" + tel + "%" : "%");
            pstmt.setString(4, email != null ? "%" + email + "%" : "%");
            pstmt.setString(5, address != null ? "%" + address + "%" : "%");


            try (ResultSet rs = pstmt.executeQuery()) {
                // 使用 while 來遍歷多個結果
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getString("Customer_id"));
                    customer.setName(rs.getString("Name"));
                    customer.setTel(rs.getString("Tel"));
                    customer.setEmail(rs.getString("Email"));
                    customer.setAddress(rs.getString("Address"));
                    customers.add(customer); // 將每個客戶添加到列表中
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(customers);
        return customers; // 確保返回搜尋結果
    }

    //依姓名(A-Z)排序
    public List<Customer> sortByNameASC() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY Name ASC";
        try (Connection conn = dbService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("Customer_id"));
                customer.setName(rs.getString("Name"));
                customer.setTel(rs.getString("Tel"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //依姓名(Z-A)排序
    public List<Customer> sortByNameDESC() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY Name DESC";
        try (Connection conn = dbService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("Customer_id"));
                customer.setName(rs.getString("Name"));
                customer.setTel(rs.getString("Tel"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //依id排序(降冪)
    public List<Customer> sortByIdDESC() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY Customer_id DESC";
        try (Connection conn = dbService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getString("Customer_id"));
                customer.setName(rs.getString("Name"));
                customer.setTel(rs.getString("Tel"));
                customer.setEmail(rs.getString("Email"));
                customer.setAddress(rs.getString("Address"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
}