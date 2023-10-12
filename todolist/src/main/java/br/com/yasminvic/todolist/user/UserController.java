package br.com.yasminvic.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/cadastro")
    //@RequestBody é pra dizer que o dado vai vir do body (formulario de cadastro)
    public ResponseEntity create(@RequestBody UserModel user){
        var username = this.userRepository.findByUsername(user.getUsername());
        
        if(username != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Funcionário já cadastrado");
        }

        //criando hash
        var passwordHash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(passwordHash);

        var userCreated = this.userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    

}
