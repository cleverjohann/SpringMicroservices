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
    public CategoriaEntity update(Integer id, CategoriaEntity categoria) {
        return categoriaRepository.findById(id).map(categoriaEntity -> {
            categoriaEntity.setNombre(categoria.getNombre());
            return categoriaRepository.save(categoriaEntity);
        }).orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));
    }

    @Override
    public boolean delete(Integer id) {
        return categoriaRepository.findById(id).map(categoriaEntity -> {
            categoriaRepository.delete(categoriaEntity);
            return true;
        }).orElseThrow(() -> new CategoriaNotFoundException("Categoria no encontrada"));
    }

    @Override
    public CategoriaEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<CategoriaEntity> findAll() {
        return categoriaRepository.findAll();
    }


}
