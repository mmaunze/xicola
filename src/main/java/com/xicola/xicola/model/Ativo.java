package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ativo", schema = "public")
public class Ativo {
    @Id
    @ColumnDefault("nextval('ativo_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;

    @Size(max = 100)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @NotNull
    @Column(name = "data_aquisicao", nullable = false)
    private LocalDate dataAquisicao;

    @NotNull
    @Column(name = "valor_aquisicao", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorAquisicao;

    @Size(max = 255)
    @Column(name = "localizacao")
    private String localizacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}