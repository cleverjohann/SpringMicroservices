package com.microservice.producto_categoria.infraestructure.services;

import com.microservice.producto_categoria.api.domain.request.ProductoRequest;
import com.microservice.producto_categoria.api.domain.responses.ProductoResponse;
import com.microservice.producto_categoria.domain.entities.Categoria;
import com.microservice.producto_categoria.domain.entities.Producto;
import com.microservice.producto_categoria.domain.repositories.CategoriaRepository;
import com.microservice.producto_categoria.domain.repositories.ProductoRepository;
import com.microservice.producto_categoria.infraestructure.abstract_services.IProductoService;
import com.microservice.producto_categoria.mappers.ProductoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Productoservice implements IProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponse create(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Producto producto = productoMapper.toEntity(request, categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponse update(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);

        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponse findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toResponse(producto);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public ProductoResponse read(Long id) {
        return findById(id);
    }
    @Override
    public List<ProductoResponse> findAll() {
        // Convertir la lista de entidades Producto a una lista de DTOs ProductoResponse
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
