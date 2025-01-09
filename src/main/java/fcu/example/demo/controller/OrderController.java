package fcu.example.demo.controller;


import fcu.example.demo.modal.Order;
import fcu.example.demo.modal.Restaurant;
import fcu.example.demo.service.OrderService;
import fcu.example.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/order")//共用路徑
public class OrderController {

    @Autowired
    OrderService orderService;

    //取得所有資料
    @GetMapping("")
    public List<Order> getAllOrder() {
        return orderService.listAll();
    }

    //取得單一資料
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }


    //新增資料
    @PostMapping("/add")
    public Order add(@RequestBody Order order) {
        // 假設 service 已處理 ID 設置等邏輯
        orderService.addOrder(order);
        return order;
    }


    //找ID刪除
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        orderService.deleteOrder(id);
    }

    //更新指定ID的資料
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable String id, @RequestBody Order order){
        orderService.updateOrder(id,order.getTime(),order.getTotalmoney(),order.getStatus(),order.getF_customer_id(),order.getF_driver_id(),order.getF_restaurant_id());
        return order;
    }



    @GetMapping("/search")
    public List<Order> searchOrder(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String totalmoney,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String f_customer_id,
            @RequestParam(required = false) String f_driver_id,
            @RequestParam(required = false) String f_restaurant_id
    )
    {
        return orderService.getOrderBySearch(id, time, totalmoney, status, f_customer_id, f_driver_id, f_restaurant_id);
    }

}
