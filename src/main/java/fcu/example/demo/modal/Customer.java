package fcu.example.demo.modal;

import lombok.Data;


@Data
public class Customer {

    private String id;
    private String name;
    private String tel;
    private String email;
    private String address;

    public Customer(String id, String name, String tel, String email, String address) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.address = address;
    }

    public Customer() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
