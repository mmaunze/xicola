package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "turma", schema = "public")
public class Turma {
    @Id
    @ColumnDefault("nextval('turma_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome_turma", nullable = false, length = 100)
    private String nomeTurma;

    @NotNull
    @Column(name = "ano_lectivo", nullable = false)
    private Integer anoLectivo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "professor_responsavel", nullable = false)
    private Professor professorResponsavel;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}