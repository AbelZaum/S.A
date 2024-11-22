package com.senai.S.A.repository;

import com.senai.S.A.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    Optional<Colaborador> findByEmail(String email);
}
