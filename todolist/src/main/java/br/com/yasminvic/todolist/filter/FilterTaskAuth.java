package br.com.yasminvic.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.yasminvic.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //assim o spring consegue gerenciar
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                //pegar a autenticação (user e senha)
                var authorization = request.getHeader("Authorization");

                /*
                 * substring é um metodo para retirar uma parte da string, podendo passar um número X ou um intervalo, de X até Y
                 * basic tem 5 letras, logo vai cortar a string nas 5 primeiras letras
                 * e o metodo trim é pra tirar todos os espaços da string
                 */
                var authEncoded = authorization.substring("Basic".length()).trim();
             
                //decodificando
                byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
                var authString = new String(authDecoded);
                String[] credentials = authString.split(":");
                
                var username = credentials[0];
                var password = credentials[1];

                //validar user
                var user = this.userRepository.findByUsername(username);
                if(user == null){
                    response.sendError(401, "Usário não autorizado");
                }else{
                    //validar senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if(passwordVerify.verified){
                        //segue viagem
                        filterChain.doFilter(request, response);
                    }else{
                        response.sendError(401, "Usário não autorizado");
                    }
                    
                }

                
    }

    
}
