package com.aponte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aponte.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario,Integer> {
    Usuario findByUsername(String username);
}
