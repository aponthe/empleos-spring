package com.aponte.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aponte.service.IVacantesService;
import com.aponte.service.ICategoriasService;
import com.aponte.model.Vacante;
import com.aponte.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

    @Value("${empleosapp.ruta.imagenes}")
    private String ruta;

    @Autowired
    @Qualifier("vacantesServiceJpa")
    private IVacantesService serviceVacantes;
    @Autowired
    @Qualifier("categoriasServiceJpa")
    private ICategoriasService serviceCategorias;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        // TODO 1. Obtener todas las vacantes (recuperarlas con la clase de servicio)
        for (Vacante vacante : serviceVacantes.buscarTodas())
            System.out.println(vacante);

        // TODO 2. Agregar al modelo el listado de vacantes
        model.addAttribute("vacantes", serviceVacantes.buscarTodas());

        // TODO 3. Renderizar las vacantes en la vista (integrar el archivo template-empleos/listVacantes.html)

        // TODO 4. Agregar al menu una opción llamada "Vacantes" configurando la URL "vacantes/index"
        return "vacantes/listVacantes";
    }

    @GetMapping("/indexPaginate")
    public String mostrarIndexPaginado(Model model,Pageable page){
        Page<Vacante> lista= serviceVacantes.buscarTodas(page);
        model.addAttribute("vacantes",lista);
        return "vacantes/listVacantes";
    }

    @GetMapping("/create")
    public String crear(Vacante vacante, Model model) {
        return "vacantes/formVacante";
    }

    @PostMapping("/save")
    public String guardar(Vacante vacante,
                          BindingResult result,
                          RedirectAttributes attributes,
                          @RequestParam("archivoImagen") MultipartFile multiPart) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors())
                System.out.println("Ocurrio un error: " + error.getDefaultMessage());
            return "vacantes/formVacante";
        }
        if (!multiPart.isEmpty()) {
            //String ruta = "/Users/gustavo/SPRING FILES/";
            String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
            if (nombreImagen != null)
                //Procesamos la variablenombreImagen
                vacante.setImagen(nombreImagen);
        }
        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg", "Registro Guardado");
        System.out.println("Vacante: " + vacante);
        //return "vacantes/listVacantes";
        return "redirect:/vacantes/index";
    }

	/*@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
						  @RequestParam("estatus") String estatus, @RequestParam("fecha") String fecha,
						  @RequestParam("destacado") int destacado, @RequestParam("salario") double salario,
						  @RequestParam("detalles") String detalles){
		String impresion=	"Nombre Vacante: "+nombre+
							"\nDescripcion: "+descripcion+
							"\nEstatus: "+estatus+
							"\nFecha Publicacion: "+fecha+
							"\nDestacado: "+ destacado+
							"\nSalario Ofrecido: "+salario+
							"\ndetalles: "+detalles;
		System.out.println(impresion);
		return "vacantes/listVacantes";
	}*/

    @GetMapping("/delete/{id}")
    //public String eliminar(@RequestParam("id") int idVacante, Model model){
    public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
        System.out.println("Borrando vacante con id: " + idVacante);
        serviceVacantes.eliminar(idVacante);
        attributes.addFlashAttribute("msg","La vacante fue eliminada");
        return "redirect:/vacantes/index";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int id, Model model){
        Vacante vacante = serviceVacantes.buscarPorId(id);
        model.addAttribute("vacante",vacante);
        return "vacantes/formVacante";
    }

    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("categorias", serviceCategorias.buscarTodas());
    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model) {
        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        System.out.println("Vacante: " + vacante);
        model.addAttribute("vacante", vacante);
        //Buscar los detalles de la vacante en la BD...
        return "detalle";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
