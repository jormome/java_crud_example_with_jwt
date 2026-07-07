package com.curso.spring.app.web;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;
import com.curso.spring.app.dto.request.PersonaRequestDto;
import com.curso.spring.app.dto.response.PersonaResponseDto;
import com.curso.spring.app.services.PersonaService;
import com.curso.spring.app.web.generics.GenericController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/personas")
public class PersonaController extends GenericController<PersonaRequestDto, PersonaResponseDto, Long> {

    private final PersonaService personaService;

    public PersonaController(
            PersonaService service,
            MessageSource messageSource) {
        super(service, "personas", messageSource);
        this.personaService = service;
    }

    @Override
    protected PersonaRequestDto createRequestDto() {
        return new PersonaRequestDto();
    }

    @GetMapping({ "", "/" })
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal User user) { // con esta anotacion obtenemos el usuario logueado

        log.info("El usuario que hizo login es: " + user);

        PageRequest pageable = PageRequest.of(page, size);
        Page<PersonaResponseDto> currentPage = service.paginatedList(pageable);

        // Si la página solicitada es mayor que el total de páginas,
        // redirigimos a la primera página (0) con el nuevo tamaño.
        if ((currentPage.getTotalPages() > 0 && page >= currentPage.getTotalPages())
                || (currentPage.getTotalPages() == 0 && page > 0)) {
            return "redirect:/" + baseRoute + "?page=0&size=" + size;
        }

        // Saldo total de TODOS los registros en BD
        Double saldoTotal = personaService.getTotalSaldo();

        // log.info("Accedint a la ruta /path");
        model.addAttribute("objects", currentPage.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("baseRoute", baseRoute);
        model.addAttribute("saldoTotal", saldoTotal);
        // Objeto vacío para el formulario del modal
        model.addAttribute("personaForm", createRequestDto());
        return "index";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("personaForm") PersonaRequestDto persona,
            BindingResult errors,
            RedirectAttributes flash,
            Model model) {

        if (errors.hasErrors()) {
            // Recargar la página con los datos de paginación y el objeto con errores
            PageRequest pageable = PageRequest.of(0, 10);
            Page<PersonaResponseDto> currentPage = personaService.paginatedList(pageable);
            Double saldoTotal = personaService.getTotalSaldo();

            model.addAttribute("objects", currentPage.getContent());
            model.addAttribute("page", currentPage);
            model.addAttribute("baseRoute", baseRoute);
            model.addAttribute("saldoTotal", saldoTotal);
            model.addAttribute("personaForm", persona); // Mantener el objeto con errores
            model.addAttribute("showModal", true); // Flag para mostrar el modal

            return "index";
        }
        personaService.save(persona);

        flash.addFlashAttribute("success", "Cliente agregado con éxito");
        return "redirect:/personas";
    }

}
