package com.senai.S.A.service;

import com.senai.S.A.model.Usuario;
import com.senai.S.A.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> autenticar(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> usuario.getSenha().equals(senha));
    }
}
