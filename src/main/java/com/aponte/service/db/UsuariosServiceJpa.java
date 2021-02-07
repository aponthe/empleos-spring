package com.aponte.service.db;

import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.service.IUsuariosService;
import com.aponte.repository.UsuariosRepository;
import com.aponte.model.Usuario;
import java.util.List;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService{
    @Autowired
    private UsuariosRepository usuariosRepo;
    public void guardar(Usuario usuario){usuariosRepo.save(usuario);}
    public void eliminar(Integer id){usuariosRepo.deleteById(id);}
    public List<Usuario> buscarTodos(){return usuariosRepo.findAll();}
    public Page<Usuario> buscarTodos(Pageable page){return usuariosRepo.findAll(page);}
    public Usuario buscarPorUsername(String username){return usuariosRepo.findByUsername(username);}
}
