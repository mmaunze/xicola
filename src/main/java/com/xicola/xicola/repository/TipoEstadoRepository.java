package com.xicola.xicola.repository;

import com.xicola.xicola.model.TipoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEstadoRepository extends JpaRepository<TipoEstado, Long> {


}
