package com.aponte.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.service.IUsuariosService;
import com.aponte.model.Usuario;

import java.util.List;

@Service
public class UsuariosServiceImpl implements IUsuariosService{
    public void guardar(Usuario usuario){}
    public void eliminar(Integer id){}
    public List<Usuario> buscarTodos(){return null;}
    public Page<Usuario> buscarTodos(Pageable page){return null;}
    public Usuario buscarPorUsername(String username){return null;}
}
