package com.xicola.xicola.service;

import com.xicola.xicola.model.SectorTrabalho;
import com.xicola.xicola.repository.SectorTrabalhoRepository;
import com.xicola.xicola.service.exceptions.BadRequestException;
import com.xicola.xicola.service.exceptions.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SectorTrabalhoService {

    private static final String SECTOR_TRABALHO_NOT_FOUND_MESSAGE = "Setor de trabalho não encontrado com o ID: ";

    private final SectorTrabalhoRepository sectorTrabalhoRepository;

    @Transactional(readOnly = true)
    public SectorTrabalho findById(Long id) {
        return sectorTrabalhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SECTOR_TRABALHO_NOT_FOUND_MESSAGE + id));
    }

    @Transactional(readOnly = true)
    public SectorTrabalho findSector(String sector) {
        return sectorTrabalhoRepository.findSector(sector)
                .orElseThrow(() -> new ResourceNotFoundException(SECTOR_TRABALHO_NOT_FOUND_MESSAGE + sector));
    }

    @Transactional(readOnly = true)
    public List<SectorTrabalho> findAll() {
        return sectorTrabalhoRepository.findAll();
    }

    @Transactional
    public SectorTrabalho create(SectorTrabalho sectorTrabalho) {
        validarSectorTrabalho(sectorTrabalho);

        return sectorTrabalhoRepository.save(sectorTrabalho);
    }

    @Transactional
    public SectorTrabalho update(Long id, SectorTrabalho sectorTrabalhoAtualizado) {
        var sectorTrabalhoExistente = sectorTrabalhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SECTOR_TRABALHO_NOT_FOUND_MESSAGE + id));

        validarSectorTrabalho(sectorTrabalhoAtualizado);

        sectorTrabalhoExistente.setDescricao(sectorTrabalhoAtualizado.getDescricao());

        return sectorTrabalhoRepository.save(sectorTrabalhoExistente);
    }

    @Transactional
    public void delete(Long id) {
        var sectorTrabalho = sectorTrabalhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SECTOR_TRABALHO_NOT_FOUND_MESSAGE + id));

        sectorTrabalhoRepository.delete(sectorTrabalho);
    }

    private void validarSectorTrabalho(SectorTrabalho sectorTrabalho) {
        if (sectorTrabalho.getDescricao() == null || sectorTrabalho.getDescricao().isEmpty()) {
            throw new BadRequestException("A descrição do setor de trabalho não pode ser nula ou vazia.");
        }

        // Adicione outras validações conforme necessário
    }
}
