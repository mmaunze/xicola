package com.xicola.xicola.repository;

import com.xicola.xicola.model.ParticipanteEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteEventoRepository extends JpaRepository<ParticipanteEvento, Integer> {


}
