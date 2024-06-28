package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tipo_documento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_documento_descricao_key", columnNames = {"descricao"})
})
public class TipoDocumento {
    @Id
    @ColumnDefault("nextval('tipo_documento_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 70)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 70)
    private String descricao;

}