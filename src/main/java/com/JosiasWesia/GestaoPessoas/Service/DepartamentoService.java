package com.JosiasWesia.GestaoPessoas.Service;

import com.JosiasWesia.GestaoPessoas.Dto.DepartamentoDto;
import com.JosiasWesia.GestaoPessoas.Dto.DepartamentoInput;
import com.JosiasWesia.GestaoPessoas.Exception.BusinessException;
import com.JosiasWesia.GestaoPessoas.Exception.ResourceConflictException;
import com.JosiasWesia.GestaoPessoas.Model.Departamento;
import com.JosiasWesia.GestaoPessoas.Repository.DepartamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartamentoService {

    private final DepartamentoRepository repo;

    public DepartamentoService(DepartamentoRepository repo) {
        this.repo = repo;
    }

    public List<DepartamentoDto> listarTodos() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<DepartamentoDto> listarAtivos() {
        return repo.findAll().stream()
                .filter(Departamento::isAtivo)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DepartamentoDto criar(DepartamentoInput input) {
        if (repo.existsByNomeIgnoreCase(input.getNome())) {
            throw new ResourceConflictException("Departamento já existente: " + input.getNome());
        }
        Departamento dep = new Departamento(input.getNome(), input.getSigla());
        return toDto(repo.save(dep));
    }

    public DepartamentoDto atualizar(Long id, DepartamentoInput input) {
        Optional<Departamento> opt = repo.findById(id);
        if (opt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Departamento não encontrado.");
        }

        Departamento dep = opt.get();
        if (!dep.getNome().equalsIgnoreCase(input.getNome()) &&
                repo.existsByNomeIgnoreCase(input.getNome())) {
            throw new ResourceConflictException("Já existe um departamento com esse nome.");
        }

        dep.setNome(input.getNome());
        dep.setSigla(input.getSigla());
        return toDto(repo.save(dep));
    }

    public DepartamentoDto inativar(Long id) {
        Departamento dep = repo.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Departamento não encontrado."));
        dep.setAtivo(false);
        return toDto(repo.save(dep));
    }

    private DepartamentoDto toDto(Departamento d) {
        return new DepartamentoDto(d.getId(), d.getNome(), d.getSigla(), d.isAtivo());
    }
}