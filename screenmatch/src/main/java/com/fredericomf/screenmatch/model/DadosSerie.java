package com.fredericomf.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
                String imdbID,
                @JsonAlias("Title") String titulo, @JsonAlias("totalSeasons") String totalTemporadas,
                @JsonAlias("imdbRating") String avaliacao) {

}
