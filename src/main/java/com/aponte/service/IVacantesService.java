package com.aponte.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.model.Vacante;
import java.util.List;

public interface IVacantesService {
    List<Vacante> buscarTodas();
    Vacante buscarPorId(Integer idVacante);
    void guardar(Vacante vacante);
    List<Vacante> buscarDestacadas();
    void eliminar(Integer id);
    List<Vacante> buscarByExample(Example<Vacante> example);
    Page<Vacante> buscarTodas(Pageable page);
}
