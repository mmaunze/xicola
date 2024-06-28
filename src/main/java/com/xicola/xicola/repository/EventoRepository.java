package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

}