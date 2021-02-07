package com.aponte.service.db;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.service.IVacantesService;
import com.aponte.model.Vacante;
import com.aponte.repository.VacantesRepository;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {

    @Autowired
    private VacantesRepository vacantesRepo;

    public List<Vacante> buscarTodas() {
        return vacantesRepo.findAll();
    }

    public Vacante buscarPorId(Integer idVacante) {
        Optional<Vacante> v = vacantesRepo.findById(idVacante);
        return v.isPresent() ? v.get() : null;
    }

    public void guardar(Vacante v) {
        vacantesRepo.save(v);
    }

    public List<Vacante> buscarDestacadas() {
        return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
    }

    public void eliminar(Integer id) {
        vacantesRepo.deleteById(id);
    }

    public List<Vacante> buscarByExample(Example<Vacante> example) {
        return vacantesRepo.findAll(example);
    }

    @Override
    public Page<Vacante> buscarTodas(Pageable page){
        return vacantesRepo.findAll(page);
    }
}
