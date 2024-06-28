package com.xicola.xicola.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xicola.xicola.model.Aluno;
import com.xicola.xicola.model.Disciplina;
import com.xicola.xicola.model.Estado;
import com.xicola.xicola.model.PresencasAluno;
import com.xicola.xicola.model.Turma;
import com.xicola.xicola.repository.AlunoRepository;
import com.xicola.xicola.repository.DisciplinaRepository;
import com.xicola.xicola.repository.EstadoRepository;
import com.xicola.xicola.repository.PresencasAlunoRepository;
import com.xicola.xicola.repository.TurmaRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PresencasAlunoService {

    private static final String PRESENCAS_ALUNO_NOT_FOUND_MESSAGE = "Presença do aluno não encontrada com o ID: ";
    private static final String ALUNO_NOT_FOUND_MESSAGE = "Aluno não encontrado com o ID: ";
    private static final String DISCIPLINA_NOT_FOUND_MESSAGE = "Disciplina não encontrada com o ID: ";
    private static final String ESTADO_NOT_FOUND_MESSAGE = "Estado não encontrado com o ID: ";
    private static final String TURMA_NOT_FOUND_MESSAGE = "Turma não encontrada com o ID: ";

    private final PresencasAlunoRepository presencasAlunoRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final EstadoRepository estadoRepository;
    private final TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public PresencasAluno findById(Integer id) {
        return presencasAlunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRESENCAS_ALUNO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<PresencasAluno> findAll() {
        return presencasAlunoRepository.findAll();
    }

    @Transactional
    public PresencasAluno create(PresencasAluno presencasAluno) {
        validarPresencasAluno(presencasAluno);

        return presencasAlunoRepository.save(presencasAluno);
    }

    @Transactional
    public PresencasAluno update(Integer id, PresencasAluno presencasAlunoAtualizada) {
        PresencasAluno presencasAlunoExistente = presencasAlunoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRESENCAS_ALUNO_NOT_FOUND_MESSAGE + id));

        validarPresencasAluno(presencasAlunoAtualizada);

        presencasAlunoExistente.setData(presencasAlunoAtualizada.getData());
        presencasAlunoExistente.setAluno(presencasAlunoAtualizada.getAluno());
        presencasAlunoExistente.setDisciplina(presencasAlunoAtualizada.getDisciplina());
        presencasAlunoExistente.setEstado(presencasAlunoAtualizada.getEstado());
        presencasAlunoExistente.setTurma(presencasAlunoAtualizada.getTurma());

        return presencasAlunoRepository.save(presencasAlunoExistente);
    }

    @Transactional
    public void delete(Integer id) {
        if (!presencasAlunoRepository.existsById(id)) {
            throw new ResourceNotFoundException(PRESENCAS_ALUNO_NOT_FOUND_MESSAGE + id);
        }
        presencasAlunoRepository.deleteById(id);
    }

    private void validarPresencasAluno(PresencasAluno presencasAluno) {
        validarData(Date.from(presencasAluno.getData()));
        validarAluno(presencasAluno);
        validarDisciplina(presencasAluno);
        validarEstado(presencasAluno);
        validarTurma(presencasAluno);
    }

    private void validarData(Date data) {
        if (data.after(new Date())) {
            throw new BadRequestException("A data de presença não pode estar no futuro.");
        }
    }

    private void validarAluno(PresencasAluno presencasAluno) {
        Aluno aluno = alunoRepository.findById(presencasAluno.getAluno().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ALUNO_NOT_FOUND_MESSAGE + presencasAluno.getAluno().getId()));
        presencasAluno.setAluno(aluno);
    }

    private void validarDisciplina(PresencasAluno presencasAluno) {
        Disciplina disciplina = disciplinaRepository.findById(presencasAluno.getDisciplina().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        DISCIPLINA_NOT_FOUND_MESSAGE + presencasAluno.getDisciplina().getId()));
        presencasAluno.setDisciplina(disciplina);
    }

    private void validarEstado(PresencasAluno presencasAluno) {
        Estado estado = estadoRepository.findById(presencasAluno.getEstado().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ESTADO_NOT_FOUND_MESSAGE + presencasAluno.getEstado().getId()));
        presencasAluno.setEstado(estado);
    }

    private void validarTurma(PresencasAluno presencasAluno) {
        Turma turma = turmaRepository.findById(presencasAluno.getTurma().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        TURMA_NOT_FOUND_MESSAGE + presencasAluno.getTurma().getId()));
        presencasAluno.setTurma(turma);
    }
}
