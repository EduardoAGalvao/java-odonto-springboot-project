package br.senai.sp.odonto.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
=======
import org.springframework.security.core.Authentication;
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//@Component -> anotação que identifica a classe para o Spring, candidata à autodetecção do mesmo
@Component
public class JwtAuthenticationService {
	
	//Criando constante para armazenar uma palavra-chave em seus bytes
	//que será passada para a Base64 (padrão que envolve números e letras para outra palavra ou número)
	private static final String SECRET_KEY = Base64.getEncoder().encodeToString("Senai127".getBytes());
	
	//Prefixo que será utilizado junto à SECRET_KEY
	private static final String PREFIX = "Bearer";
	
	//Será utilizado quando não houver o token
	private static final String EMPTY = "";
	
	//Nome do parâmetro que setará os dados de token
	private static final String AUTHORIZATION = "Authorization";
	
	//Tempo para expirar o token, no caso está sendo passado 1 dia em formato de milissegundos
	private static final long EXPIRATION_TIME = 86400000;
	
<<<<<<< HEAD
	//Importando o UserDetailServiceImpl, que busca o usuário no banco e suas informações
	@Autowired
	private UserDetailServiceImpl userDetailService;
	
	//Método para criação de token recebendo o nome do usuário e suas permissões
	public String createToken(String username, List<String> roles) {
=======
	//Método para criação de token recebendo o nome do usuário e suas permissões
	private String createToken(String username, List<String> roles) {
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
		
		//Criação do payload para geração do token
		//iniciando setando o valor do parâmetro "sub" com o username
		Claims claims = Jwts.claims().setSubject(username);
		
		//inserindo mais dados no payload com o método put(), no caso inserindo um parâmetro customizado chamado roles
		claims.put("roles", roles);
		
		//Coletando o tempo atual
		Date now = new Date();
		
		Date validity = new Date(now.getTime() + EXPIRATION_TIME);
		
		String token = Jwts.builder()
						.setClaims(claims)
						.setIssuedAt(now)
						.setExpiration(validity)
						.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
						.compact();
		
		return token;
		
	}
	
<<<<<<< HEAD
	//Método para autenticar de acordo com a requisição
	public Authentication getAuthentication(HttpServletRequest req) {
		
		//Através da requisição, obtém o token
		String token = resolveToken(req);
		
		//Se o token não estiver vazio e for um token válido, prossegue
		if(token != null && validateToken(token)) {
			
			//Atualmente o username está alocado dentro do parâmetro sub
			//referente a subject, por isso está sendo coletado através dele
			String username = Jwts
					.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			
			if(username != null) {
				UserDetails userDetails = userDetailService.loadUserByUsername(username);
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			}
			
		}
		
		return null;
		
	}
	
	//Método que avalia se o token é válido 
	private boolean validateToken(String token) {
		
		//Avaliando a validade do token através da SECRET_KEY
		Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
		
		//Avaliando a validade do token dentro do período de expiração, comparando com a hora atual 
		if(claims.getBody().getExpiration().before(new Date())) {
			return false;
		}
		
		return true;
	}
	
	//Método que separa o token do restante da requisição
=======
	public Authentication getAuthentication(HttpServletRequest req) {
		
		String token = resolveToken(req);
		
		if(token != null) {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			
			if(claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
		}
		
		return true;
		
	}
	
>>>>>>> be8a69bcbd6fa704a6662d866718848f4761fc6c
	public String resolveToken(HttpServletRequest req) {
		
		//Coletando o parâmetro de Authorization 
		String bearerToken = req.getHeader(AUTHORIZATION);
		
		//Checando se o que há em Authorization possui conteúdo
		//e se esse conteúdo começa com o prefixo Bearer
		if(bearerToken != null && bearerToken.startsWith(PREFIX)) {
			return bearerToken.replace(PREFIX, EMPTY).trim();
		}
		
		//Retornando nulo caso tenha passado if
		return null;
		
	}

}
