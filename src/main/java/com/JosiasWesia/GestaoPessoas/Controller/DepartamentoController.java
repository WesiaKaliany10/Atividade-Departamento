package com.JosiasWesia.GestaoPessoas.Controller;

import com.JosiasWesia.GestaoPessoas.Dto.DepartamentoDto;
import com.JosiasWesia.GestaoPessoas.Dto.DepartamentoInput;
import com.JosiasWesia.GestaoPessoas.Service.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
@CrossOrigin(origins = "*")
public class DepartamentoController {

    private final DepartamentoService service;

    public DepartamentoController(DepartamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<DepartamentoDto> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/ativos")
    public List<DepartamentoDto> listarAtivos() {
        return service.listarAtivos();
    }

    @PostMapping
    public ResponseEntity<DepartamentoDto> criar(@RequestBody DepartamentoInput input) {
        return ResponseEntity.status(201).body(service.criar(input));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDto> atualizar(@PathVariable Long id, @RequestBody DepartamentoInput input) {
        return ResponseEntity.ok(service.atualizar(id, input));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<DepartamentoDto> inativar(@PathVariable Long id) {
        return ResponseEntity.ok(service.inativar(id));
    }
}