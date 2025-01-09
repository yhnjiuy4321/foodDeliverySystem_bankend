package fcu.example.demo.modal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Menu {
    private String id;
    private String name;
    private int cost;
    private double score;
    private String f_restaurant_id;
    private String photo;


    public Menu(String id, String name, int cost, double score, String f_restaurant_id, String photo) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.score = score;
        this.f_restaurant_id = f_restaurant_id;
        this.photo = photo;
    }

    public Menu() {

    }

}
