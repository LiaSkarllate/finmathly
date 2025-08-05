package dev.liaskarllate.finmathly.modality.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.liaskarllate.finmathly.modality.entity.Modality;

public interface ModalityRepository extends JpaRepository<Modality, UUID>, JpaSpecificationExecutor<Modality> {
    boolean existsByName(String name);

    Optional<Modality> findByName(String name);
}
