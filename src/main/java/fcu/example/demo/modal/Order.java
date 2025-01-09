package fcu.example.demo.modal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order {

    private String id;
    private String time;
    private int totalmoney;
    private String status;
    private String f_restaurant_id;
    private String f_driver_id;
    private String f_customer_id;
    private String f_dish_id;


    public Order(String id, String time, String status,int totalmoney, String f_restaurant_id, String f_driver_id, String f_customer_id, String f_dish_id) {
        this.id = id;
        this.time = time;
        this.status = status;
        this.totalmoney = totalmoney;
        this.f_restaurant_id = f_restaurant_id;
        this.f_driver_id = f_driver_id;
        this.f_customer_id = f_customer_id;
        this.f_dish_id = f_dish_id;


    }

    public Order() {

    }


}
