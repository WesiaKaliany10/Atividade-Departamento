package com.JosiasWesia.GestaoPessoas.Controller;

import com.JosiasWesia.GestaoPessoas.Dto.FuncionarioRequest;
import com.JosiasWesia.GestaoPessoas.Dto.FuncionarioResponse;
import com.JosiasWesia.GestaoPessoas.Service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")

public class FuncionarioController {

    private final FuncionarioService service;

    @Autowired
    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listar(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) Boolean ativo) {

        List<FuncionarioResponse> funcionarios = service.listar(Optional.ofNullable(cargo), Optional.ofNullable(ativo));
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Long id) {
        FuncionarioResponse funcionario = service.buscarPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponse> criar(@Validated @RequestBody FuncionarioRequest request) {
        FuncionarioResponse criado = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable Long id,
                                                         @Validated @RequestBody FuncionarioRequest request) {
        FuncionarioResponse atualizado = service.atualizar(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}