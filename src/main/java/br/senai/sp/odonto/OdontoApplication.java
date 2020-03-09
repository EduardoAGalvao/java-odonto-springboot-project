package br.senai.sp.odonto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//scanBasePackages = {"com.vz.spring.items"} -> escaneia os pacotes em busca de algum que talvez não tenha anotações necessárias para a execução
//testado em 06/03
@SpringBootApplication(scanBasePackages = {"com.vz.spring.items"})
public class OdontoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdontoApplication.class, args);
	}

}
