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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public ProductoResponse createProduct(ProductoRequest productRequest) {
        CategoriaEntity categoriaEntity = categoriaRepository.findById(productRequest.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));

        //Convertir ProductoRequest a ProductoEntity
        ProductoEntity productoEntity = productoMapper.toProduct(productRequest);

        //Guardar el producto en la base de datos
        ProductoEntity productoEntitySaved = productoRepository.save(productoEntity);
        return productoMapper.toProductResponse(productoEntitySaved);
    }

    @Override
    public ProductoResponse getProductById(Long id) {
        ProductoEntity productoEntity = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        return productoMapper.toProductResponse(productoEntity);
    }

    @Override
    public List<ProductoResponse> getAllProducts() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toProductResponse)
                .toList();
    }

    @Override
    public ProductoResponse updateProduct(Long id, ProductoRequest productRequest) {
        //Verificar si la categoria existe
        CategoriaEntity categoriaEntity = categoriaRepository.findById(productRequest.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));
        //Buscar el producto por su ID
        ProductoEntity productoEntity = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado"));
        //Actualizar los datos del producto
        productoEntity.setNombre(productRequest.getNombre());
        productoEntity.setDescripcion(productRequest.getDescripcion());
        productoEntity.setPrecio(new BigDecimal(productRequest.getPrecio()));
        productoEntity.setStock(productRequest.getStock());
        productoEntity.setCategoriaId(productRequest.getCategoriaId());

        //Guardar los cambios en la base de datos
        ProductoEntity productoEntitySaved = productoRepository.save(productoEntity);

        return productoMapper.toProductResponse(productoEntitySaved);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

}