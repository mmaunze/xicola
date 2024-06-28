package com.xicola.xicola.controller;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xicola.xicola.model.AvaliacaoAluno;
import com.xicola.xicola.model.dto.AvaliacaoAlunoDTO;
import com.xicola.xicola.service.AlunoService;
import com.xicola.xicola.service.AvaliacaoAlunoService;
import com.xicola.xicola.service.AvaliacaoService;
import com.xicola.xicola.service.EstadoService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/academico/avalicoes-alunos")
@RequiredArgsConstructor
@Slf4j
public class AvaliacaoAlunoController {

    private final AvaliacaoAlunoService avaliacaoAlunoService;
    private final AlunoService alunoService;
    private final EstadoService estadoService;
    private final AvaliacaoService avaliacaoService;


    @GetMapping
    public ResponseEntity<List<AvaliacaoAlunoDTO>> findAll() {
        try {
            Iterable<AvaliacaoAluno> avaliacaoAlunos = avaliacaoAlunoService.findAll();
            List<AvaliacaoAlunoDTO> avaliacaoAlunosDTO = StreamSupport.stream(avaliacaoAlunos.spliterator(), false)
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(avaliacaoAlunosDTO, OK);
        } catch (Exception e) {
            log.error("Erro ao buscar todas as avaliações de alunos", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/avaliacao-aluno/{id}")
    public ResponseEntity<AvaliacaoAlunoDTO> findById(@PathVariable Long id) {
        try {
            AvaliacaoAluno avaliacaoAluno = avaliacaoAlunoService.findById(id);
            return ResponseEntity.ok(convertToDTO(avaliacaoAluno));
        } catch (EntityNotFoundException e) {
            log.error("Avaliação de aluno não encontrada com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao buscar avaliação de aluno com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AvaliacaoAlunoDTO avaliacaoAlunoDTO) {
        try {
            AvaliacaoAluno newAvaliacaoAluno = convertToEntity(avaliacaoAlunoDTO);
            AvaliacaoAluno createdAvaliacaoAluno = avaliacaoAlunoService.create(newAvaliacaoAluno);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(createdAvaliacaoAluno.getId()).toUri();
            return created(location).build();
        } catch (Exception e) {
            log.error("Erro ao criar nova avaliação de aluno", e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AvaliacaoAlunoDTO avaliacaoAlunoDTO) {
        try {
            AvaliacaoAluno updatedAvaliacaoAluno = convertToEntity(avaliacaoAlunoDTO);
            avaliacaoAlunoService.update(id, updatedAvaliacaoAluno);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Avaliação de aluno não encontrada para o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao atualizar avaliação de aluno com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            avaliacaoAlunoService.delete(id);
            return ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Avaliação de aluno não encontrada para remoção com o ID: " + id, e);
            return new ResponseEntity<>(NOT_FOUND);
        } catch (Exception e) {
            log.error("Erro ao remover avaliação de aluno com o ID: " + id, e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    private AvaliacaoAluno convertToEntity(AvaliacaoAlunoDTO avaliacaoAlunoDTO) {
        AvaliacaoAluno avaliacaoAluno = new AvaliacaoAluno();
        avaliacaoAluno.setId(avaliacaoAlunoDTO.getId());
        avaliacaoAluno.setAluno(alunoService.findById(avaliacaoAlunoDTO.getAluno()));
        avaliacaoAluno.setAvaliacao(avaliacaoService.findById(avaliacaoAlunoDTO.getAvaliacao()));
        avaliacaoAluno.setTrimestre(avaliacaoAlunoDTO.getTrimestre());
        avaliacaoAluno.setAnoLectivo(avaliacaoAlunoDTO.getAnoLectivo());
        avaliacaoAluno.setDataLancamento(avaliacaoAlunoDTO.getDataLancamento());
        avaliacaoAluno.setNota(avaliacaoAlunoDTO.getNota());
        avaliacaoAluno.setObservacao(avaliacaoAlunoDTO.getObservacao());
        

        avaliacaoAluno.setEstado(estadoService.findEstado(avaliacaoAlunoDTO.getEstado())
                .orElseThrow(
                        () -> new EntityNotFoundException("Estado não encontrado: " + avaliacaoAlunoDTO.getEstado())));

        return avaliacaoAluno;
    }

    private AvaliacaoAlunoDTO convertToDTO(AvaliacaoAluno avaliacaoAluno) {
        return new AvaliacaoAlunoDTO(avaliacaoAluno);
     
    }
}
