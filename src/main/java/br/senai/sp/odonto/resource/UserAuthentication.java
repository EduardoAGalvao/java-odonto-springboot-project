package br.senai.sp.odonto.resource;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.dto.UserAccountCredential;
import br.senai.sp.odonto.model.User;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.odonto.dto.UserAccountCredential;
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
import br.senai.sp.odonto.repository.UserRepository;
import br.senai.sp.odonto.security.JwtAuthenticationService;

@RestController
<<<<<<< HEAD
@RequestMapping("/odonto")
=======
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
public class UserAuthentication {
	
	//Busca os dados do usuário no banco
	@Autowired
	private UserRepository userRepository;
	
	//Faz a criação do token
	@Autowired
	private JwtAuthenticationService jwtService;
	
	//Faz a autenticação verificando se é um usuário válido
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//Método para logar
<<<<<<< HEAD
	//Aqui será o caminho obrigatório que o usuário deverá seguir quando acessar
	@PostMapping("/auth/login")
	public ResponseEntity<Map<Object, Object>> signIn(@RequestBody UserAccountCredential credential) {
=======
	public void signIn(@RequestBody UserAccountCredential credential) {
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
		
		UsernamePasswordAuthenticationToken userCredential = new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword());
		
		authenticationManager.authenticate(userCredential);
		
<<<<<<< HEAD
		List<String> roles = new ArrayList<String>();
		
		//Bloco para adquirir as funções do usuário
		User userLogin = new User();
		userLogin = userRepository.findByUsername(credential.getUsername());
		roles.add(userLogin.getRole());
		
		String token = jwtService.createToken(credential.getUsername(), roles);
		
		//Método Map -> registra valores no conjunto chave (key) e valor (value)
		Map<Object, Object> jsonResponse = new HashMap<>();
		
		jsonResponse.put("username", credential.getUsername());
		jsonResponse.put("token", token);
		
		return ResponseEntity.ok(jsonResponse);
		
=======
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
	}

}
