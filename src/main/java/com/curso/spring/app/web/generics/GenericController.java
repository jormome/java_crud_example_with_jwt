package com.curso.spring.app.web.generics;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.curso.spring.app.dto.request.generics.GenericRequestDto;
import com.curso.spring.app.dto.response.generics.GenericResponseDto;
import com.curso.spring.app.services.generics.GenericService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
public abstract class GenericController<RQ extends GenericRequestDto<ID>, RS extends GenericResponseDto<ID>, ID extends Serializable> {

    // inyectamos el repositorio con todos los métodos
    protected final GenericService<RQ, RS, ID> service;
    protected String baseRoute;
    private final MessageSource messageSource;

    public GenericController(
            GenericService<RQ, RS, ID> service,
            String baseRoute,
            MessageSource messageSource) {

        this.service = service;
        this.baseRoute = baseRoute;
        this.messageSource = messageSource;
    }

    protected abstract RQ createRequestDto();

    @GetMapping({ "", "/" })
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User user) { // con esta anotacion obtenemos el usuario logueado

        log.info("El usuario que hizo login es: " + user);

        PageRequest pageable = PageRequest.of(page, size);
        Page<RS> currentPage = service.paginatedList(pageable);

        // Si la página solicitada es mayor que el total de páginas,
        // redirigimos a la primera página (0) con el nuevo tamaño.
        if ((currentPage.getTotalPages() > 0 && page >= currentPage.getTotalPages())
                || (currentPage.getTotalPages() == 0 && page > 0)) {
            return "redirect:/" + baseRoute + "?page=0&size=" + size;
        }

        // log.info("Accedint a la ruta /path");
        model.addAttribute("objects", currentPage.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("baseRoute", baseRoute);
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) { // Spring buscara dentro del factory
        // este constructor es para un record como dto
        model.addAttribute("objects", createRequestDto());
        // de spring este objeto sino lo crea
        return "modify"; // redirije a modificar.html
    }

    // pasado por PATH VARIABLE
    // al tener el objeto un atributo identico al pasado por el path, se lanza el
    // setId de la persona con el id del path
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable ID id, Model model) {
        // lo inyectamos al model con la persona encontrada
        model.addAttribute("objects", service.findById(id));
        // redirijimos y cargamos la persona a la pagina modificar
        return "modify";
    }

    @PostMapping("/save")
    // el objeto se inyecta al ser creado por el mapping /agregar
    // el @Valid ---, Errors errors deben estar juntos al objeto y luego ya despues
    // permito mas parametros
    public String save(
            @Valid @ModelAttribute("objects") RQ object,
            BindingResult errors,
            RedirectAttributes flash) {

        if (errors.hasErrors()) {
            return "modify"; // si hay errores no dejo avanzar y vuelvo a la ventana modificacion
        }
        service.save(object);

        // Estos atributos "sobreviven" a la redirección solo una vez
        flash.addFlashAttribute("success", "Operación realizada con éxito");

        return "redirect:/" + baseRoute; // redireccionamos a la pagina
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable ID id, RedirectAttributes flash, Locale locale) {
        service.delete(id);
        String message = messageSource.getMessage("flash.success.message", null, locale);
        flash.addFlashAttribute("success", message);
        return "redirect:/" + baseRoute;
    }
}