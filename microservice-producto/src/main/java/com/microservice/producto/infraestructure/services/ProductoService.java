package com.microservice.producto.infraestructure.services;

import com.microservice.categoria.domain.entities.CategoriaEntity;
import com.microservice.categoria.domain.repositories.CategoriaRepository;
import com.microservice.categoria.util.CategoriaNotFoundException;
import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;
import com.microservice.producto.domain.repositories.ProductoRepository;
import com.microservice.producto.infraestructure.abstract_services.IProductoService;
import com.microservice.producto.mappers.ProductoMapper;
import com.microservice.producto.util.ProductoNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }


    @Override
    public ProductoEntity save(ProductoEntity producto) {
        CategoriaEntity categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Override
public ProductoEntity update(Integer id, ProductoEntity producto) {
    return productoRepository.findById(id).map(productoEntity -> {
        // Llamada al microservicio de categoría para obtener la categoría actualizada
        CategoriaEntity categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));

        productoEntity.setNombre(producto.getNombre());
        productoEntity.setDescripcion(producto.getDescripcion());
        productoEntity.setPrecio(producto.getPrecio());
        productoEntity.setStock(producto.getStock());
        productoEntity.setCategoria(categoria);
        return productoRepository.save(productoEntity);
    }).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
}

    @Override
    public boolean delete(Integer id) {
        return productoRepository.findById(id).map(productoEntity -> {
            productoRepository.delete(productoEntity);
            return true;
        }).orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductoEntity findById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();
    }
}
