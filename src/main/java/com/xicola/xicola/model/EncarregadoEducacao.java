package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "encarregado_educacao", schema = "public")
public class EncarregadoEducacao {
    @Id
    @ColumnDefault("nextval('encarregado_educacao_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("nextval('encarregado_educacao_id_seq'::regclass)")
    @JoinColumn(name = "id", nullable = false)
    private Utilizador utilizador;

    @Size(max = 150)
    @NotNull
    @Column(name = "nome_completo", nullable = false, length = 150)
    private String nomeCompleto;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "distrito_nascimento")
    private Distrito distritoNascimento;

    @Size(max = 1)
    @NotNull
    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @Size(max = 255)
    @Column(name = "local_trabalho")
    private String localTrabalho;

    @Column(name = "sector_trabalho")
    private Integer sectorTrabalho;

    @Size(max = 255)
    @Column(name = "endereco")
    private String endereco;

    @Size(max = 75)
    @Column(name = "email", length = 75)
    private String email;

    @Size(max = 3)
    @Column(name = "grupo_sanguineo", length = 3)
    private String grupoSanguineo;

    @NotNull
    @Column(name = "numero_telefone_principal", nullable = false)
    private Long numeroTelefonePrincipal;

    @Column(name = "numero_telefone_alternativo")
    private Long numeroTelefoneAlternativo;

}