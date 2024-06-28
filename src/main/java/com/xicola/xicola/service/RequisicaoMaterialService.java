package com.xicola.xicola.service;

import com.xicola.xicola.model.RequisicaoMaterial;
import com.xicola.xicola.repository.RequisicaoMaterialRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequisicaoMaterialService {

    private static final String REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE = "Requisição de material não encontrada com o ID: ";

    private final RequisicaoMaterialRepository requisicaoMaterialRepository;

    @Transactional(readOnly = true)
    public RequisicaoMaterial findById(Long id) {
        return requisicaoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public List<RequisicaoMaterial> findAll() {
        return requisicaoMaterialRepository.findAll();
    }

    @Transactional
    public RequisicaoMaterial create(RequisicaoMaterial requisicaoMaterial) {
        validarRequisicaoMaterial(requisicaoMaterial);

        return requisicaoMaterialRepository.save(requisicaoMaterial);
    }

    @Transactional
    public RequisicaoMaterial update(Long id, RequisicaoMaterial requisicaoMaterialAtualizada) {
        RequisicaoMaterial requisicaoMaterialExistente = requisicaoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE + id));

        validarRequisicaoMaterial(requisicaoMaterialAtualizada);

        requisicaoMaterialExistente.setDataRequisicao(requisicaoMaterialAtualizada.getDataRequisicao());
        requisicaoMaterialExistente.setQuantidade(requisicaoMaterialAtualizada.getQuantidade());
        requisicaoMaterialExistente.setObservacao(requisicaoMaterialAtualizada.getObservacao());
        requisicaoMaterialExistente.setEstado(requisicaoMaterialAtualizada.getEstado());
        requisicaoMaterialExistente.setRequisitor(requisicaoMaterialAtualizada.getRequisitor());
        requisicaoMaterialExistente.setMaterial(requisicaoMaterialAtualizada.getMaterial());

        return requisicaoMaterialRepository.save(requisicaoMaterialExistente);
    }

    @Transactional
    public void delete(Long id) {
        RequisicaoMaterial requisicaoMaterial = requisicaoMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(REQUISICAO_MATERIAL_NOT_FOUND_MESSAGE + id));

        requisicaoMaterialRepository.delete(requisicaoMaterial);
    }

    private void validarRequisicaoMaterial(RequisicaoMaterial requisicaoMaterial) {
        if (requisicaoMaterial.getDataRequisicao() == null
                || requisicaoMaterial.getDataRequisicao().isAfter(new Date().toInstant())) {
            throw new BadRequestException(
                    "A data de requisição do material não pode ser nula e deve estar no passado.");
        }

        if (requisicaoMaterial.getQuantidade() <= 0) {
            throw new BadRequestException("A quantidade requisitada deve ser maior que zero.");
        }

        // Adicione outras validações conforme necessário
    }
}
