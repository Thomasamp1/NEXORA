package com.nexora.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nexora.api.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}