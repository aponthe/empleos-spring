package com.aponte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.aponte.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante,Integer>{

    //select * from Vacantes where estatus=?
    List<Vacante> findByEstatus(String estatus);

    //select * from Vacantes where
    List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);

    List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double s1,double s2);

    List<Vacante> findByEstatusIn(String[] estatus);


}
