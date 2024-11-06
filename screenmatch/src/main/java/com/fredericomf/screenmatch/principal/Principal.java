package com.fredericomf.screenmatch.principal;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fredericomf.screenmatch.model.DadosEpisodio;
import com.fredericomf.screenmatch.model.DadosSerie;
import com.fredericomf.screenmatch.model.DadosTemporada;
import com.fredericomf.screenmatch.model.Episodio;
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

        // Testar trocar para map e ver o que acontece.
        List<DadosEpisodio> dadosEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("Top 5 episódios:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        System.out.println("A partir de qual ano você deseja ver os episódios?");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data lançamento: " + formatador.format(e.getDataLancamento())));

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
