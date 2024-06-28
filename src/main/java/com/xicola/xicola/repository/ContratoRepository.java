package com.xicola.xicola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xicola.xicola.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

}
