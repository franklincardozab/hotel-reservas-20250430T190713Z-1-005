package com.hotel.controller;

import com.staybook.model.Usuario;
import com.staybook.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UsuarioService usuarioService;

    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    public String mostrarPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(userDetails.getUsername());
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/perfil")
    public String actualizarPerfil(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam String nombre,
                                   @RequestParam String telefono) {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(userDetails.getUsername());
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuarioService.guardarUsuario(usuario);
        return "redirect:/perfil?success";
    }
}
