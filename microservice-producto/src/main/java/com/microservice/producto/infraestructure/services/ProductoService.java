package com.microservice.producto.infraestructure.services;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;
import com.microservice.producto.domain.repositories.ProductoRepository;
import com.microservice.producto.infraestructure.abstract_services.CrudService;
import com.microservice.producto.mappers.ProductoMapper;
import com.microservice.producto.util.ProductoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoService implements CrudService<ProductoRequest , ProductoResponse, Long> {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public ProductoResponse create(ProductoRequest request) {
        ProductoEntity entity = productoMapper.requestToEntity(request);
        ProductoEntity savedEntity = productoRepository.save(entity);
        return productoMapper.entityToResponse(savedEntity);
    }

    @Override
    public ProductoResponse update(Long id, ProductoRequest request) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        entity.setNombre(request.getNombre());
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());
        ProductoEntity savedEntity = productoRepository.save(entity);
        return productoMapper.entityToResponse(savedEntity);
    }

    @Override
    public ProductoResponse delete(Long id) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        productoRepository.delete(entity);
        return productoMapper.entityToResponse(entity);
    }

    @Override
    public ProductoResponse findById(Long id) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return productoMapper.entityToResponse(entity);
    }

    @Override
    public ProductoResponse findAll() {
        List<ProductoEntity> entities = productoRepository.findAll();
        return (ProductoResponse) productoMapper.entityToResponseList(entities);
    }
}
