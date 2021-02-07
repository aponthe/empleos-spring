package com.aponte.controller;

import java.util.Date;
import java.util.List;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

import com.aponte.model.Vacante;
import com.aponte.model.Perfil;
import com.aponte.service.IVacantesService;
import com.aponte.service.IUsuariosService;
import com.aponte.model.Usuario;
import com.aponte.service.ICategoriasService;

@Controller
public class HomeController {

    @Autowired
    private IVacantesService serviceVacantes;
    @Autowired
    private IUsuariosService serviceUsuarios;
    @Autowired
    private ICategoriasService serviceCategorias;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String mostrarHome(Model model) {
        return "home";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication auth, HttpSession session) {
        System.out.println("Nombre del usuario: " + auth.getName());
        for (GrantedAuthority rol : auth.getAuthorities())
            System.out.println("ROL: " + rol.getAuthority());
        if (session.getAttribute("usuario") == null) {
            Usuario u = serviceUsuarios.buscarPorUsername(auth.getName());
            u.setPassword(null);
            System.out.println("Usuario: " + u);
            session.setAttribute("usuario", u);
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String registrarse(Usuario usuario) {
        return "formRegistro";
    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Perfil perfil = new Perfil();
        perfil.setPerfil("USUARIO");
        perfil.setId(3);

        usuario.setFechaRegistro(new Date());
        usuario.agregar(perfil);
        usuario.setEstatus(1);
        serviceUsuarios.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario registrado");
        System.out.println("Usuario: " + usuario);
        return "redirect:/usuarios/index";
    }

    @GetMapping("/tabla")
    public String mostrarTabla(Model model) {
        List<Vacante> lista = serviceVacantes.buscarTodas();//getVacante();
        model.addAttribute("vacantes", lista);
        return "tabla";
    }

    @GetMapping("/detalle")
    public String mostrarDetalle(Model model) {
        Vacante vacante = new Vacante();
        vacante.setNombre("Ingeniero de comunicaciones");
        vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
        vacante.setFecha(new Date());
        vacante.setSalario(9700.0);
        model.addAttribute("vacante", vacante);
        return "detalle";
    }

    @GetMapping("/listado")
    public String mostrarListado(Model model) {
        List<String> lista = new LinkedList<String>();
        lista.add("Ingeniero de Sistemas");
        lista.add("Auxiliar de Contabilidad");
        lista.add("Vendedor");
        lista.add("Arquitecto");

        model.addAttribute("empleos", lista);
        return "listado";
    }

    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
        System.out.println("Buscando por: " + vacante);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("descripcion",
                        ExampleMatcher //where descripcion like '%?%'
                                .GenericPropertyMatchers
                                .contains());

        Example<Vacante> example = Example.of(vacante, matcher);
        List<Vacante> lista = serviceVacantes.buscarByExample(example);
        model.addAttribute("vacantes", lista);
        return "home";
    }

    @GetMapping("/login")
    public String mostrarLogin(){
        return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SecurityContextLogoutHandler logoutHandler=
                new SecurityContextLogoutHandler();
        logoutHandler.logout(request,null,null);
        return "redirect:/login";
    }

    @GetMapping("/bcrypt/{texto}")
    @ResponseBody
    public String encriptar(@PathVariable("texto") String texto) {
        return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public void setGenericos(Model model) {
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset();
        model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
        model.addAttribute("categorias", serviceCategorias.buscarTodas());
        model.addAttribute("search", vacanteSearch);
    }
}
