package com.aponte.service.db;

import com.aponte.model.Categoria;
import com.aponte.service.ICategoriasService;
import com.aponte.repository.CategoriasRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriasService {

    @Autowired
    private CategoriasRepository categoriasRepo;

    public void guardar(Categoria categoria) {
        categoriasRepo.save(categoria);
    }


    public List<Categoria> buscarTodas() {
        return categoriasRepo.findAll();
    }


    public Categoria buscarPorId(Integer idCategoria) {
        Optional<Categoria> optional=categoriasRepo.findById(idCategoria);
        return (optional.isPresent())? optional.get() : null;
    }

    public void eliminar(Integer id){
        categoriasRepo.deleteById(id);
    }

    public Page<Categoria> buscarTodas(Pageable page){return categoriasRepo.findAll(page);}
}
