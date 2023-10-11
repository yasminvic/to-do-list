package br.com.yasminvic.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "tb_users") //nome da tabela no bd
@Data // responsavel por criar Getters e Setter da classe
public class UserModel {
    
    @Id
    @GeneratedValue(generator = "UUID") //gerar hash automático
    private UUID id; //tipo um hash, sequencia de letras e números

    @Column(unique = true) //faz com que só possa ter um username por conta
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt; //local que foi criado
}


/** Se quisermos que tal classe tenha só Getters, colocar @Getter em cima da classe
 * Se quisermos que tal classe tenha só Setter, colocar @Setter em cima da classe
 * Se quisermos os dois, colocar @Data em cima da classe
 * 
 * Se quisermos definir algo pra um atributo espeficico, colocar @ em cima dele e dizer qual dos 3 tipos quer
 */