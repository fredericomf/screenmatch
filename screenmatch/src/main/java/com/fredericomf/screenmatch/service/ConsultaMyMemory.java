package com.fredericomf.screenmatch.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredericomf.screenmatch.model.DadosTraducao;

public class ConsultaMyMemory {

    public static String obterTraducao(String text) {

        DadosTraducao traducao;
        try {
            ObjectMapper mapper = new ObjectMapper();

            ConsumoApi consumo = new ConsumoApi();

            String texto = URLEncoder.encode(text, "UTF-8");
            String langpair = URLEncoder.encode("en|pt-br", "UTF-8");

            String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;

            String json = consumo.obterDados(url);
            traducao = mapper.readValue(json, DadosTraducao.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return traducao.dadosResposta().textoTraduzido();
    }

}
