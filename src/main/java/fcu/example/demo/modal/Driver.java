package fcu.example.demo.modal;

public class Driver {
    private String id;
    private String name;
    private String tel;
    private String email;
    private double score;
    private int num_of_delivery;

    public Driver(String id, String name, String tel, String email, double score, int num_of_delivery) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.score = score;
        this.num_of_delivery = num_of_delivery;
    }

    public Driver() {

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNum_of_delivery() {
        return num_of_delivery;
    }

    public void setNum_of_delivery(int num_of_delivery) {
        this.num_of_delivery = num_of_delivery;
    }
}
