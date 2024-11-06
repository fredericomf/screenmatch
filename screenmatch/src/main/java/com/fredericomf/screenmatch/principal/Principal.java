package com.fredericomf.screenmatch.principal;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fredericomf.screenmatch.model.DadosSerie;
import com.fredericomf.screenmatch.model.DadosTemporada;
import com.fredericomf.screenmatch.service.ConsumoApi;
import com.fredericomf.screenmatch.service.ConverteDados;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final static String ENDERECO = "https://www.omdbapi.com";
    private final static String API_KEY = "&apikey=54abc975";

    public void exibeMenu() {

        System.out.println("Digite o nome da série para busca: ");

        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(montarURLBusca(nomeSerie));

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= Integer.parseInt(dados.totalTemporadas()); i++) {
            json = consumo.obterDados(montarURLConsulta(dados.imdbID(), i));
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }

    // Preferi implementar esses métodos auxiliares, para uma implementação mais
    // limpa.
    private String montarURLBusca(String textoBusca) {
        return ENDERECO + "?t=" + sanitizarEntradaTextual(textoBusca) + API_KEY;
    }

    private String montarURLConsulta(String id, Integer temporada) {
        return ENDERECO + "?i=" + id + "&season=" + temporada + API_KEY;
    }

    private String sanitizarEntradaTextual(String texto) {
        return URLEncoder.encode(texto, StandardCharsets.UTF_8).replace("%20", "+");
    }

}
