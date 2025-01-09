package fcu.example.demo.controller;

import fcu.example.demo.modal.Menu;
import fcu.example.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @Value("${upload.pathMenu}")
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
        Menu menu = menuService.getMenuByRestaurantAndMenuId(id);
        if(menu == null){
            return "找不到該菜單";
        }
        menu.setPhoto("/uploads/dishes/" + fileName);
        menuService.updateMenu(id, menu.getName(), menu.getCost(), menu.getScore(), menu.getF_restaurant_id(), menu.getPhoto());

        //回傳圖片路徑
        return "/uploads/dishes/"+fileName;
    }



    //取得所有資料
    @GetMapping("")
    public List<Menu> getAllMenu() {
        return menuService.listAll();
    }

    //取得該餐聽的菜單資料
    @GetMapping("/{id}")
    public List<Menu> getMenu(@PathVariable String id) {
        return menuService.getMenu(id);
    }

    @GetMapping("/{id_res}/{id_m}")
    public Menu getCertainMenu(@PathVariable String id_m) {
        // 處理邏輯，根據 id_res 和 id_m 獲取菜單
        return menuService.getMenuByRestaurantAndMenuId(id_m);
    }

    //新增資料
    @PostMapping("/add")
    public Menu add(@RequestBody Menu menu) {
        // 假設 service 已處理 ID 設置等邏輯
        menuService.create(menu);
        return menu;
    }

    //找ID刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        menuService.deleteMenu(id);
    }

    //更新指定ID的資料
    @PutMapping("/{id}")
    public Menu updateMenu(@PathVariable String id, @RequestBody Menu menu){
        menuService.updateMenu(id, menu.getName(), menu.getCost(), menu.getScore(), menu.getF_restaurant_id(), menu.getPhoto());
        return menu;
    }

    @GetMapping("/{Restaurant_id}/search")
    public List<Menu> searchMenu(
            @PathVariable String Restaurant_id,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name
    )
    {
        return menuService.gstMenuBySearch(Restaurant_id, id, name);
    }
}
