package dev.liaskarllate.finmathly.modality.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.repository.ModalityRepository;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ModalityServiceTest {
    @Mock
    private ModalityRepository modalityRepository;

    @InjectMocks
    private ModalityService modalityService;

    private Modality modality(UUID id, String name) {
        Modality modality = new Modality(id, name);
        modality.setYieldType(YieldType.FIXED);
        modality.setCapitalizationPeriod(CapitalizationPeriod.MONTHLY);
        modality.setSupportsFlows(false);
        return modality;
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenSavingDuplicateName() {
        Modality modality = modality(null, "LTN");

        when(modalityRepository.existsByName("LTN")).thenReturn(true);

        assertThatThrownBy(() -> modalityService.save(modality))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonexistentModality() {
        UUID id = UUID.randomUUID();
        Modality modality = modality(id, "LTN");

        when(modalityRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> modalityService.update(modality))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenRenamingToAnExistingName() {
        UUID id = UUID.randomUUID();
        Modality oldEntity = modality(id, "LTN");
        Modality modality = modality(id, "LFT");

        when(modalityRepository.existsById(id)).thenReturn(true);
        when(modalityRepository.findById(id)).thenReturn(Optional.of(oldEntity));
        when(modalityRepository.existsByName("LFT")).thenReturn(true);

        assertThatThrownBy(() -> modalityService.update(modality))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonexistentModality() {
        UUID id = UUID.randomUUID();

        when(modalityRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> modalityService.deleteById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenCheckingNonexistentModalityExistence() {
        UUID id = UUID.randomUUID();

        when(modalityRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> modalityService.checkModalityExistence(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
