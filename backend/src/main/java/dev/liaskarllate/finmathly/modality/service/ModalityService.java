package dev.liaskarllate.finmathly.modality.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.modality.specification.ModalitySearchSpecificationBuilder;
import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchFilter;
import dev.liaskarllate.finmathly.modality.repository.ModalityRepository;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ModalityService {
    private final ModalityRepository modalityRepository;

    public Page<Modality> findByFilter(ModalitySearchFilter filter, Pageable pageable) {
        Specification<Modality> spec = ModalitySearchSpecificationBuilder.build(filter);
        return this.modalityRepository.findAll(spec, pageable);
    }

    public Modality findById(UUID id) {
        return this.modalityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The modality with the id " + id + " was not found."));
    }

    @Transactional
    public Modality save(Modality modality) {
        this.ensureModalityCanBeSaved(modality);
        return this.modalityRepository.save(modality);
    }

    private void ensureModalityCanBeSaved(Modality modality) {
        if (this.modalityRepository.existsByName(modality.getName())) {
            throw new ResourceAlreadyExistsException("The modality with the name '" + modality.getName() + "' already exists.");
        }
    }

    @Transactional
    public Modality update(Modality modality) {
        this.ensureModalityCanBeUpdated(modality);
        return this.modalityRepository.save(modality);
    }

    private void ensureModalityCanBeUpdated(Modality modality) {
        Modality existingModality = this.modalityRepository.findById(modality.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The modality with the id '" + modality.getId() + "' was not found. Please, provide a valid id."));

        if (!existingModality.getName().equals(modality.getName()) &&
                this.modalityRepository.existsByName(modality.getName())) {
            throw new ResourceAlreadyExistsException("The modality with the name '" + modality.getName() + "' already exists. Please, provide a different name.");
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        this.ensureModalityCanBeDeleted(id);
        this.modalityRepository.deleteById(id);
    }

    private void ensureModalityCanBeDeleted(UUID id) {
        if (!this.modalityRepository.existsById(id)) {
            throw new ResourceNotFoundException("The modality with the id '" + id + "' was not found.");
        }
    }
}