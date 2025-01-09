package fcu.example.demo.modal;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Manager {

    private String id;
    private String name;
    private String tel;
    private String email;
    private String account;
    private String password;


    public Manager(String id, String name, String tel, String email, String account, String password) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.account = account;
        this.password = password;
    }

    public Manager() {
        
    }
}
