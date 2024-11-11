package com.fredericomf.screenmatch.model;

public enum Categoria {

    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String categoriaOmdb) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(categoriaOmdb)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada: " + categoriaOmdb);
    }

}
