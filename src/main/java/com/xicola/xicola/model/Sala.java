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
@Table(name = "sala", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "sala_nome_sala_key", columnNames = {"nome_sala"})
})
public class Sala {
    @Id
    @ColumnDefault("nextval('sala_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome_sala", nullable = false, length = 100)
    private String nomeSala;

    @NotNull
    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

}