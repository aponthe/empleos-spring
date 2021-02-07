package com.aponte.service.db;

import java.util.List;
import java.util.Optional;

import com.aponte.model.Solicitud;
import com.aponte.service.ISolicitudesService;
import com.aponte.repository.SolicitudesRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

public class SolicitudesServiceJpa implements ISolicitudesService {

    @Autowired
    private SolicitudesRepository solicitudesRepo;

    public void guardar(Solicitud solicitud) {
        solicitudesRepo.save(solicitud);
    }

    public void eliminar(Integer idSolicitud) {
        solicitudesRepo.deleteById(idSolicitud);
    }

    public List<Solicitud> buscarTodas() {
        return solicitudesRepo.findAll();
    }

    public Solicitud buscarPorId(Integer idSolicitud) {
        Optional<Solicitud> optional = solicitudesRepo.findById(idSolicitud);
        return optional.isPresent() ? optional.get() : null;
    }

    public Page<Solicitud> buscarTodas(Pageable page) {
        return solicitudesRepo.findAll(page);
    }

}
