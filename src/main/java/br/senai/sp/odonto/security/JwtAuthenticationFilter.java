package br.senai.sp.odonto.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean{
	
	private JwtAuthenticationService jwtAuthenticationService;
	
	public JwtAuthenticationFilter(JwtAuthenticationService jwtAuthenticationService) {
		this.jwtAuthenticationService = jwtAuthenticationService;
	}

	//A ideia do filtro é interceptar as requisições
	//deixando prosseguir se estiver tudo bem
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//Recebe o retorno de uma tentativa de autenticação enviando o request
		Authentication authentication = jwtAuthenticationService.getAuthentication( (HttpServletRequest) request);
		
		//Seta a autenticação retornada
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//A corrente de dados usa um filtro pelo request e tem uma resposta caso esteja tudo ok
		chain.doFilter(request, response);
		
	}

}
