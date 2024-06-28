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
@Table(name = "tipo_transacao", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_transacao_descricao_key", columnNames = {"descricao"})
})
public class TipoTransacao {
    @Id
    @ColumnDefault("nextval('tipo_transacao_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

}