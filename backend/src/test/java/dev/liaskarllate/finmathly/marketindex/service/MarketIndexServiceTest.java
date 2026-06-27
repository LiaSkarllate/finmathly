package dev.liaskarllate.finmathly.marketindex.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.repository.MarketIndexRepository;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class MarketIndexServiceTest {
    @Mock
    private MarketIndexRepository marketIndexRepository;

    @InjectMocks
    private MarketIndexService marketIndexService;

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenSavingDuplicateName() {
        MarketIndex marketIndex = new MarketIndex(null, "SELIC");

        when(marketIndexRepository.existsByName("SELIC")).thenReturn(true);

        assertThatThrownBy(() -> marketIndexService.save(marketIndex))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonexistentMarketIndex() {
        UUID id = UUID.randomUUID();
        MarketIndex marketIndex = new MarketIndex(id, "SELIC");

        when(marketIndexRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> marketIndexService.update(marketIndex))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenRenamingToAnExistingName() {
        UUID id = UUID.randomUUID();
        MarketIndex oldEntity = new MarketIndex(id, "SELIC");
        MarketIndex marketIndex = new MarketIndex(id, "IPCA");

        when(marketIndexRepository.existsById(id)).thenReturn(true);
        when(marketIndexRepository.findById(id)).thenReturn(Optional.of(oldEntity));
        when(marketIndexRepository.existsByName("IPCA")).thenReturn(true);

        assertThatThrownBy(() -> marketIndexService.update(marketIndex))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonexistentMarketIndex() {
        UUID id = UUID.randomUUID();

        when(marketIndexRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> marketIndexService.deleteById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenFindingNonexistentMarketIndexById() {
        UUID id = UUID.randomUUID();

        when(marketIndexRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> marketIndexService.findById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
