package com.JosiasWesia.GestaoPessoas.Dto;

public class DepartamentoDto {
    private Long id;
    private String nome;
    private String sigla;
    private Boolean ativo;

    public DepartamentoDto() {}

    public DepartamentoDto(Long id, String nome, String sigla, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.ativo = ativo;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getSigla() { return sigla; }
    public Boolean getAtivo() { return ativo; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSigla(String sigla) { this.sigla = sigla; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}