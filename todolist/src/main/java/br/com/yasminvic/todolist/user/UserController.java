package br.com.yasminvic.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/cadastro")
    //@RequestBody é pra dizer que o dado vai vir do body (formulario de cadastro)
    public void create(@RequestBody UserModel user){
        System.out.println(user.name);
    }
    

}
