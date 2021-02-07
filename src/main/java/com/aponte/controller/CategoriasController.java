package com.aponte.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.service.ICategoriasService;
import com.aponte.model.Categoria;

@Controller
@RequestMapping(value = "/categorias")
public class CategoriasController {
    @Autowired
    @Qualifier("categoriasServiceJpa")
    private ICategoriasService serviceCategorias;

    //@GetMapping("/index")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String mostrarIndex(Model model) {
        for (Categoria categoria : serviceCategorias.buscarTodas())
            System.out.println(categoria);
        model.addAttribute("categorias", serviceCategorias.buscarTodas());
        return "categorias/listCategorias";
    }

    @RequestMapping(value = "/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page) {
        model.addAttribute("categorias",serviceCategorias.buscarTodas(page));
        return "categorias/listCategorias";
    }

    //@GetMapping("/create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String crear(Categoria categoria) {
        return "categorias/formCategoria";
    }

    //@PostMapping("/save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors())
                System.err.println("Ocurrio un error: " + error.getDefaultMessage());
            return "categorias/formCategorias";
        }
        serviceCategorias.guardar(categoria);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        System.out.println("Categoria: " + categoria);
        return "redirect:/categorias/index";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable("id") int id, RedirectAttributes attributes) {
        serviceCategorias.eliminar(id);
        System.out.println("Borrando categoria con {id}: " + id);
        attributes.addFlashAttribute("msg", "La categoria fue eliminada");
        return "redirect:/categorias/index";
    }


}
