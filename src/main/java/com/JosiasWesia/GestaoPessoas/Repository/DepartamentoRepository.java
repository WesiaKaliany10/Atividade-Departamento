package com.JosiasWesia.GestaoPessoas.Repository;

import com.JosiasWesia.GestaoPessoas.Model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Departamento> findByNomeIgnoreCase(String nome);
}
