package br.senai.sp.odonto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration -> Anotação que classifica essa classe como componente de configuração
//@EnableWebSecurity -> Anotação que ativa a segurança pela web / HTTP
//WebSecurityConfigurerAdapter -> Classe abstrata que implementa questões de segurança para a aplicação
//@EnableGlobalMethodSecurity -> Habilitando segurança em toda a aplicação, alertando que está ativada
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtAuthenticationService jwtAuthenticationService;
	private UserDetailServiceImpl userDetailServiceImpl;

	//Método utilizado para a configuração da segurança na requisição HTTP
	//Reescrevendo o método original, retirando o formulário para autenticação que aparece no HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Comentado em 06/03, pois agora a autenticação será feita utilizando JWT
//		http
//			.authorizeRequests()
//			.anyRequest()
//			.authenticated()
//			.and().httpBasic()
//			.and().csrf().disable();
		//CSRF -> cross-site request forgery, desabilitado
		
		http
			//Desabilitando a autenticação básica, pois utilizará o JTW
			.httpBasic().disable()
			//Desabilitando o CSRF
			.csrf().disable()
			//Não será armazenada sessão/estado de acesso do usuário
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//A partir daqui há a definição do que acontecerá nas requisições
			//sob determinados endereços podem ser públicas com o permitAll()
			//ou ter cargos determinados para autorização de acesso
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/odonto/auth/login").permitAll()
			.antMatchers(HttpMethod.GET, "/odonto/dentistas/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/odonto/dentistas/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/odonto/dentistas/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/odonto/dentistas/**").hasRole("ADMIN")
			//Qualquer request deve exigir uma autenticação pelo token
			.anyRequest().authenticated()
			//O configurador recebe a requisição recebida e faz todos os tratamentos necessários
			.and().apply(new JwtAuthenticationConfigurer(jwtAuthenticationService));
	}
	
	//Método utilizado para a configuração da autenticação
	//Definindo a autenticação na memória por inMemoryAuthentication()
	//Definindo usuário e senha por .withUser() e .password()
	//Definindo nome de cargo por .roles(), só pode ter o nome da role, para que consiga conversar com o padrão "ROLE_funcao/cargo" lido na anotação do Resources
	//No caso dois usuários estão sendo definidos com suas funções
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		//Refatorado em 06/03 - agora realizando autenticação pelo JWT
//		auth
//			.inMemoryAuthentication()
//			.passwordEncoder(encoder)
//			.withUser("edu")
//			.password(encoder.encode("123"))
//			.roles("USER")
//			.and()
//			.withUser("admin")
//			.password(encoder.encode("admin"))
//			.roles("ADMIN")
//			.and()
//			.withUser("dentista")
//			.password(encoder.encode("dentista"))
//			.roles("DENTISTA")
//			.and()
//			.withUser("paciente")
//			.password(encoder.encode("paciente"))
//			.roles("PACIENTE");
		
		auth.userDetailsService(userDetailServiceImpl).passwordEncoder(encoder);
	}

}
