package com.fredericomf.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fredericomf.screenmatch.model.DadosSerie;
import com.fredericomf.screenmatch.service.ConsumoApi;
import com.fredericomf.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		var consumoApi = new ConsumoApi();
		var converter = new ConverteDados();
		var json = consumoApi
				.obterDados("https://www.omdbapi.com/?i=tt3896198&apikey=54abc975&t=gilmore+girls");
		var dados = converter.obterDados(json, DadosSerie.class);
		System.out.println(dados);

	}
}
