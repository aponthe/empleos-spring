package com.aponte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aponte.model.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria,Integer>{
}
