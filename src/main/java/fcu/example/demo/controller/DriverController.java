package fcu.example.demo.controller;

import fcu.example.demo.modal.Driver;
import fcu.example.demo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    //取得所有資料
    @GetMapping("")
    public List<Driver> getAllDriver() {
        return driverService.listAll();
    }

    //新增資料
    @PostMapping("/add")
    public Driver add(@RequestBody Driver driver) {
        // 假設 service 已處理 ID 設置等邏輯
        driverService.create(driver);
        return driver;
    }

    //找ID刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        driverService.deleteDriver(id);
    }

    //更新指定ID的資料
    @PutMapping("/{id}")
    public Driver updateDriver(@PathVariable String id, @RequestBody Driver driver){
        driverService.updateDriver(id, driver.getName(), driver.getTel(), driver.getEmail(), driver.getScore(), driver.getNum_of_delivery());
        return driver;
    }

    //查詢指定ID的資料
    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable String id) {
        return driverService.getDriverBySearch(id);
    }

    @GetMapping("/search")
    public List<Driver> searchDriver(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) String email
    )
    {
        return driverService.getDriverBySearch(id, name, tel, email);
    }
}
