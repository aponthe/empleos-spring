package com.aponte.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.model.Usuario;

import java.util.List;

public interface IUsuariosService {
    void guardar(Usuario usuario);
    void eliminar(Integer id);
    List<Usuario> buscarTodos();
    Page<Usuario> buscarTodos(Pageable page);
    Usuario buscarPorUsername(String username);
}