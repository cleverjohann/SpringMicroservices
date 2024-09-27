package com.microservice.producto_categoria.infraestructure.services;

import com.microservice.producto_categoria.api.domain.request.CategoriaRequest;
import com.microservice.producto_categoria.api.domain.responses.CategoriaResponse;
import com.microservice.producto_categoria.domain.entities.Categoria;
import com.microservice.producto_categoria.domain.repositories.CategoriaRepository;
import com.microservice.producto_categoria.infraestructure.abstract_services.ICategoriaService;
import com.microservice.producto_categoria.mappers.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;


    @Override
    public CategoriaResponse create(CategoriaRequest request) {
        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponse update(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponse findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return categoriaMapper.toResponse(categoria);
    }

    @Override
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoriaResponse read(Long id) {
        return findById(id);  // Puedes mantener este método para un propósito similar a findById
    }

    public List<CategoriaResponse> findAll() {
        // Usamos un stream para convertir la lista de entidades en una lista de DTOs
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }
}
