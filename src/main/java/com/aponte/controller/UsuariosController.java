package com.aponte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;

import com.aponte.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private IUsuariosService serviceUsuarios;

    @GetMapping("/index")
    public String mostrarIndex(Model model){
        System.out.println(serviceUsuarios.buscarTodos());
        model.addAttribute("usuarios",serviceUsuarios.buscarTodos());
        return "usuarios/listUsuarios";
    }

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model,Pageable page){
        model.addAttribute("usuarios",serviceUsuarios.buscarTodos(page));
        return "usuarios/listUsuarios";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int id,RedirectAttributes attributes){
        System.out.println("Borrando vacante con {id}"+id);
        serviceUsuarios.eliminar(id);
        attributes.addFlashAttribute("msg","El usuario fue eliminado");
        return "redirect:/usuarios/index";
    }

}
