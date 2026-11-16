package com.nexora.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nexora.api.model.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    List<Modulo> findByCursoIdOrderByOrdemAsc(Long cursoId);

}