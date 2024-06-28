package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_pessoa", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_pessoa_descricao_key", columnNames = {"descricao"})
})
public class TipoPessoa {
    @Id

    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 70)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 70)
    private String descricao;

}