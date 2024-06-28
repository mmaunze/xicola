package com.xicola.xicola.model.dto;

import java.io.Serializable;

import com.xicola.xicola.model.Avaliacao;

import lombok.Data;

@Data
public class AvaliacaoDTO implements Serializable {
    private Integer id;
    private Long aluno;
    private Integer tipoAvaliacao;
    private Integer trimestre;
    private Integer disciplina;
    private String observacao;
    private String estado;

    public AvaliacaoDTO() {
    }

    public AvaliacaoDTO(Avaliacao avaliacao) {
        this.id = avaliacao.getId();
        this.aluno = avaliacao.getAluno().getId();
        this.tipoAvaliacao = avaliacao.getTipoAvaliacao().getId();
        this.trimestre = avaliacao.getTrimestre();
        this.disciplina = avaliacao.getDisciplina().getId();
        this.observacao = avaliacao.getObservacao();
        this.estado = avaliacao.getEstado().getDescricao();
    }
}