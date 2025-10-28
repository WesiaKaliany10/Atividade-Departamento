package com.JosiasWesia.GestaoPessoas.Repository;

import com.JosiasWesia.GestaoPessoas.Model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmailIgnoreCase(String email);
    Optional<Funcionario> findByEmailIgnoreCaseAndAtivo(String email, boolean ativo);
    boolean existsByEmailIgnoreCase(String email);
    List<Funcionario> findAllByCargoIgnoreCaseOrderByNomeAsc(String cargo);
    List<Funcionario> findAllByAtivoOrderByNomeAsc(Boolean ativo);
    List<Funcionario> findAllByOrderByNomeAsc();
}

