package com.microservice.mock;

import com.microservice.categoria.domain.entities.CategoriaEntity;

import java.util.List;

public class DataProvider {
    public static List<CategoriaEntity> getCategorias() {
        return List.of(
                new CategoriaEntity(1L, "Categoria 1", "Descripcion 1"),
                new CategoriaEntity(2L, "Categoria 2", "Descripcion 2"),
                new CategoriaEntity(3L, "Categoria 3", "Descripcion 3")
        );
    }

    public static CategoriaEntity getCategoria() {
        return new CategoriaEntity(1L, "Categoria 1", "Descripcion 1");
    }

    public static CategoriaEntity newCategoria() {
        return new CategoriaEntity(null, "Categoria 1", "Descripcion 1");
    }
}
