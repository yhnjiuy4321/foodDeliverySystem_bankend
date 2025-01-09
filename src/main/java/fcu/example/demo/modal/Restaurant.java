package fcu.example.demo.modal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Restaurant {

    private String id;
    private String name;
    private String tel;
    private String email;
    private String address;
    private double score;
    private String photo;


    public Restaurant(String id, String name, String tel, String email, String address, double score, String photo) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.score = score;
        this.photo = photo;
    }

    public Restaurant() {

    }

}
