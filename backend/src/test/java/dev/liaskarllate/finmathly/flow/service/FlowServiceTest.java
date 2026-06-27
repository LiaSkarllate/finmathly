package dev.liaskarllate.finmathly.flow.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.liaskarllate.finmathly.asset.entity.Asset;
import dev.liaskarllate.finmathly.asset.repository.AssetRepository;
import dev.liaskarllate.finmathly.flow.entity.Flow;
import dev.liaskarllate.finmathly.flow.repository.FlowRepository;
import dev.liaskarllate.finmathly.shared.enums.EventType;
import dev.liaskarllate.finmathly.shared.exception.RelatedResourceNotFoundException;
import dev.liaskarllate.finmathly.shared.exception.UnmodifiableResourceReferenceException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class FlowServiceTest {
    @Mock
    private FlowRepository flowRepository;
    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private FlowService flowService;

    private Flow flow(UUID id, String assetName) {
        Flow flow = new Flow();
        flow.setId(id);
        flow.setAsset(new Asset(null, assetName));
        flow.setType(EventType.INTEREST);
        flow.setEventDate(LocalDate.now());
        flow.setAmount(new BigDecimal("50.00"));
        flow.setAmortizationPercentage(BigDecimal.ZERO);
        return flow;
    }

    @Test
    void shouldThrowRelatedResourceNotFoundExceptionWhenSavingFlowForNonexistentAsset() {
        Flow flow = flow(null, "Nonexistent Asset");

        when(assetRepository.existsByName("Nonexistent Asset")).thenReturn(false);

        assertThatThrownBy(() -> flowService.save(flow))
                .isInstanceOf(RelatedResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonexistentFlow() {
        UUID id = UUID.randomUUID();
        Flow flow = flow(id, "Some Asset");

        when(flowRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> flowService.update(flow))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonexistentFlow() {
        UUID id = UUID.randomUUID();

        when(flowRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> flowService.deleteById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowUnmodifiableResourceReferenceExceptionWhenChangingFlowAssetOnUpdate() {
        UUID id = UUID.randomUUID();
        Flow oldEntity = flow(id, "Original Asset");
        Flow flow = flow(id, "Different Asset");

        when(flowRepository.existsById(id)).thenReturn(true);
        when(flowRepository.findById(id)).thenReturn(Optional.of(oldEntity));

        assertThatThrownBy(() -> flowService.update(flow))
                .isInstanceOf(UnmodifiableResourceReferenceException.class);
    }
}
