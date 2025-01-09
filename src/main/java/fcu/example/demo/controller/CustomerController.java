package fcu.example.demo.controller;

import fcu.example.demo.modal.Customer;
import fcu.example.demo.service.CustomerService;
import fcu.example.demo.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/customer")//共用路徑
public class CustomerController {

    @Autowired
    CustomerService customerService;

    //List<Customer> customers = new ArrayList<>();

    //取得所有資料
    @GetMapping("")
    public List<Customer> getAllCustomer() {
        return customerService.listAll();
    }

    //新增資料
    @PostMapping("/add")
    public Customer add(@RequestBody Customer customer) {
        // 假設 service 已處理 ID 設置等邏輯
        customerService.create(customer);
        return customer;
    }


    //找ID刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }


    //更新指定ID的資料
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable String id, @RequestBody Customer customer){
        customerService.updateCustomer(id, customer.getName(), customer.getTel(), customer.getEmail(), customer.getAddress());
        return customer;
    }

    //查詢指定ID的資料
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerService.getCustomer(id);
    }


    @GetMapping("/search")
    public List<Customer> searchCustomer(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address) {

        // 呼叫 service 方法並傳入參數進行搜尋
        return customerService.getCustomerBySearch(id, name, tel, email, address);
    }

    @GetMapping("/nameASC")
    public List<Customer> getCustomerNameASC() {
        return customerService.sortByNameASC();
    }

    @GetMapping("/nameDESC")
    public List<Customer> getCustomerNameDESC() {
        return customerService.sortByNameDESC();
    }

    @GetMapping("/idDESC")
    public List<Customer> getCustomerIDASC() {
        return customerService.sortByIdDESC();
    }
}
