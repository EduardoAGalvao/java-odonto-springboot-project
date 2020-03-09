package br.senai.sp.odonto.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	private JwtAuthenticationService jwtAuthenticationService;
	
	public JwtAuthenticationConfigurer(JwtAuthenticationService jwtAuthenticationService) {
		this.jwtAuthenticationService = jwtAuthenticationService;
	}
	
	//Método que configura como as requisições serão tratadas, no caso passando pelo filtro
	@Override
	public void configure(HttpSecurity builder) throws Exception {
		
		//Utiliza o filtro para interceptar a requisição recebida usando um objeto com os dados de autenticação
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtAuthenticationService);
		
		builder.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
