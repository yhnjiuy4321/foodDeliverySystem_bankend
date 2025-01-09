package fcu.example.demo.controller;

import fcu.example.demo.modal.Manager;
import fcu.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/login")//共用路徑

public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("")
    public Manager checkLogin(@RequestBody Manager manager) {
        return loginService.checkLogin(manager.getAccount(), manager.getPassword());
    }


    @GetMapping("/all")
    public List<Manager> getAllManager() {
        return loginService.listAll();
    }

}
