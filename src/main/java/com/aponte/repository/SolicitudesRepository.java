package com.aponte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aponte.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud,Integer>{

}
