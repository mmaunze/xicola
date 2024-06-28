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
@Table(name = "tipo_material", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_material_descricao_key", columnNames = {"descricao"})
})
public class TipoMaterial {
    @Id
    @ColumnDefault("nextval('tipo_material_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

}