package com.nexora.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nexora.api.model.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByModuloIdOrderByOrdemAsc(Long moduloId);

}