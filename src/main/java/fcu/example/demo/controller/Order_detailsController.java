package fcu.example.demo.controller;


import fcu.example.demo.modal.Order_details;
import fcu.example.demo.service.Order_detailsService;
import fcu.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_details")//共用路徑
public class Order_detailsController {

    @Autowired
    Order_detailsService order_detailsService;

    //取得所有資料
    @GetMapping("")
    public List<Order_details> getAllOrder_details() {
        return order_detailsService.listAll();
    }

    //取得單一資料
    @GetMapping("/{id}")
    public Order_details getOrder_details(@PathVariable String id) {
        return order_detailsService.getOrder_details(id);
    }

    //新增資料
    @PostMapping("/add")
    public Order_details add(@RequestBody Order_details order_details) {
        // 假設 service 已處理 ID 設置等邏輯
        order_detailsService.addOrder_details(order_details);
        return order_details;
    }

    //找ID刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        order_detailsService.deleteOrder_details(id);
    }

    //更新指定ID的資料
    @PutMapping("/{id}")
    public Order_details updateOrder_details(@PathVariable String id, @RequestBody Order_details order_details){
        order_detailsService.updateOrder_details(id,order_details.getF_r_id(),order_details.getF_m_id());
        return order_details;
    }
}
