package fcu.example.demo.controller;


import fcu.example.demo.modal.Restaurant;
import fcu.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/restaurant")//共用路徑
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Value("${upload.path}")  // upload.path
    private String uploadDir;

    @PostMapping("/upload/{id}")
    public String uploadPhoto(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "請選擇一張圖片上傳";
        }

        // 獲取文件名稱並設置保存路徑
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());// 保存文件到伺服器

        // 更新資料庫中的圖片路徑
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if(restaurant == null){
            return "找不到該餐廳";
        }
        restaurant.setPhoto("/uploads/" + fileName);
        restaurantService.updateRestaurant(id, restaurant.getName(), restaurant.getTel(), restaurant.getEmail(), restaurant.getAddress(), restaurant.getScore(), restaurant.getPhoto());

        //回傳圖片路徑
        return fileName;
    }




    //取得所有資料
    @GetMapping("")
    public List<Restaurant> getAllRestaurant() {
        return restaurantService.listAll();
    }

    //取得單一資料
    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable String id) {
        return restaurantService.getRestaurant(id);
    }

    //新增資料
    @PostMapping("/add")
    public Restaurant add(@RequestBody Restaurant restaurant) {
        // 假設 service 已處理 ID 設置等邏輯
        restaurantService.create(restaurant);
        return restaurant;
    }

    //找ID刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
    }

    //更新指定ID的資料
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable String id, @RequestBody Restaurant restaurant){
        restaurantService.updateRestaurant(id, restaurant.getName(), restaurant.getTel(), restaurant.getEmail(), restaurant.getAddress(), restaurant.getScore(), restaurant.getPhoto());
        return restaurant;
    }

    @GetMapping("/search")
    public List<Restaurant> searchRestaurant(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String address) {

        // 呼叫 service 方法並傳入參數進行搜尋
        return restaurantService.getRestaurantBySearch(id, name, tel, email, address);
    }
}
