package com.microservice.categoria.infraestructure.services;

import com.microservice.categoria.domain.entities.CategoriaEntity;
import com.microservice.categoria.domain.repositories.CategoriaRepository;
import com.microservice.categoria.infraestructure.abstract_service.ICategoriaService;
import com.microservice.categoria.util.CategoriaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    @Override
    public CategoriaEntity save(CategoriaEntity categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public CategoriaEntity update(Long id, CategoriaEntity categoria) {
        return categoriaRepository.findById(id).map(categoriaEntity -> {
            categoriaEntity.setNombre(categoria.getNombre());
            categoriaEntity.setDescripcion(categoria.getDescripcion());
            return categoriaRepository.save(categoriaEntity);
        }).orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));
    }

    @Override
    public boolean delete(Long id) {
        return categoriaRepository.findById(id).map(categoriaEntity -> {
            categoriaRepository.delete(categoriaEntity);
            return true;
        }).orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));
    }

    @Override
    public CategoriaEntity findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public List<CategoriaEntity> findAll() {
        return categoriaRepository.findAll();
    }


}