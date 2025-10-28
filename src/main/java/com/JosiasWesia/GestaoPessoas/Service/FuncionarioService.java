package com.JosiasWesia.GestaoPessoas.Service;

import org.springframework.transaction.annotation.Transactional;
import com.JosiasWesia.GestaoPessoas.Dto.FuncionarioRequest;
import com.JosiasWesia.GestaoPessoas.Dto.FuncionarioResponse;
import com.JosiasWesia.GestaoPessoas.Exception.ConflitoException;
import com.JosiasWesia.GestaoPessoas.Exception.RecursoNaoEncontradoException;
import com.JosiasWesia.GestaoPessoas.Exception.RegraNegocioException;
import com.JosiasWesia.GestaoPessoas.Model.Funcionario;
import com.JosiasWesia.GestaoPessoas.Repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repo;

    public FuncionarioService(FuncionarioRepository repo) {
        this.repo = repo;
    }

    private FuncionarioResponse toResponse(Funcionario f) {
        return new FuncionarioResponse(
                f.getId(), f.getNome(), f.getEmail(), f.getCargo(),
                f.getSalario(), f.getDataAdmissao(), f.getAtivo()
        );
    }

    private void validarCamposEmBranco(FuncionarioRequest r) {
        if (r.getNome().trim().isEmpty() || r.getCargo().trim().isEmpty() || r.getEmail().trim().isEmpty()) {
            throw new RegraNegocioException("Nenhum campo pode conter apenas espaços em branco");
        }
    }

    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest req) {
        validarCamposEmBranco(req);

        Optional<Funcionario> existe = repo.findByEmailIgnoreCase(req.getEmail().trim());
        if (existe.isPresent()) {
            Funcionario f = existe.get();
            if (Boolean.FALSE.equals(f.getAtivo())) {
                f.setNome(req.getNome().trim());
                f.setCargo(req.getCargo().trim());
                f.setSalario(req.getSalario());
                f.setDataAdmissao(req.getDataAdmissao());
                f.setAtivo(true);
                Funcionario saved = repo.save(f);
                return toResponse(saved);
            } else {
                throw new ConflitoException("Email já cadastrado");
            }
        }

        Funcionario novo = new Funcionario();
        novo.setNome(req.getNome().trim());
        novo.setEmail(req.getEmail().trim());
        novo.setCargo(req.getCargo().trim());
        novo.setSalario(req.getSalario());
        novo.setDataAdmissao(req.getDataAdmissao());
        novo.setAtivo(true);

        Funcionario saved = repo.save(novo);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> listar(Optional<String> cargo, Optional<Boolean> ativo) {
        List<Funcionario> lista;
        if (cargo.isPresent()) {
            lista = repo.findAllByCargoIgnoreCaseOrderByNomeAsc(cargo.get());
        } else if (ativo.isPresent()) {
            lista = repo.findAllByAtivoOrderByNomeAsc(ativo.get());
        } else {
            lista = repo.findAllByOrderByNomeAsc();
        }
        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(Long id) {
        Funcionario f = repo.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Funcionário não encontrado"));
        return toResponse(f);
    }

    @Transactional
    public FuncionarioResponse atualizar(Long id, FuncionarioRequest req) {
        validarCamposEmBranco(req);
        Funcionario existente = repo.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Funcionário não encontrado"));

        if (Boolean.FALSE.equals(existente.getAtivo())) {
            throw new RegraNegocioException("Somente funcionários ativos podem ser editados");
        }

        if (!existente.getEmail().equalsIgnoreCase(req.getEmail()) &&
                repo.existsByEmailIgnoreCase(req.getEmail())) {
            throw new RegraNegocioException("Email já existe no sistema");
        }

        if (req.getSalario().compareTo(existente.getSalario()) < 0) {
            throw new RegraNegocioException("Salário não pode ser reduzido");
        }

        existente.setNome(req.getNome().trim());
        existente.setCargo(req.getCargo().trim());
        existente.setSalario(req.getSalario());
        existente.setDataAdmissao(req.getDataAdmissao());

        Funcionario saved = repo.save(existente);
        return toResponse(saved);
    }

    @Transactional
    public void inativar(Long id) {
        Funcionario existente = repo.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Funcionário não encontrado"));
        existente.setAtivo(false);
        repo.save(existente);
    }
}