package dev.liaskarllate.finmathly.asset.service;

import static org.assertj.core.api.Assertions.assertThat;
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
import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.marketindex.repository.MarketIndexRepository;
import dev.liaskarllate.finmathly.modality.entity.Modality;
import dev.liaskarllate.finmathly.modality.repository.ModalityRepository;
import dev.liaskarllate.finmathly.modality.service.ModalityService;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;
import dev.liaskarllate.finmathly.shared.exception.MissingArgumentException;
import dev.liaskarllate.finmathly.shared.exception.RelatedResourceNotFoundException;
import dev.liaskarllate.finmathly.shared.exception.ResourceAlreadyExistsException;
import dev.liaskarllate.finmathly.shared.exception.global.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {
    @Mock
    private ModalityService modalityService;
    @Mock
    private AssetRepository assetRepository;
    @Mock
    private MarketIndexRepository marketIndexRepository;
    @Mock
    private ModalityRepository modalityRepository;

    @InjectMocks
    private AssetService assetService;

    private Modality modality(UUID id, String name, YieldType yieldType) {
        Modality modality = new Modality(null, name);
        modality.setYieldType(yieldType);
        modality.setCapitalizationPeriod(CapitalizationPeriod.MONTHLY);
        modality.setSupportsFlows(false);
        return modality;
    }

    private Asset asset(UUID id, String name, Modality modality) {
        Asset asset = new Asset(id, name);
        asset.setModality(modality);
        asset.setMaturityDate(LocalDate.now().plusYears(1));
        return asset;
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonexistentAsset() {
        UUID id = UUID.randomUUID();
        Modality fixedModality = modality(UUID.randomUUID(), "LTN", YieldType.FIXED);
        Asset asset = asset(id, "Asset A", fixedModality);

        when(assetRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> assetService.update(asset))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonexistentAsset() {
        UUID id = UUID.randomUUID();

        when(assetRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> assetService.deleteById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldThrowResourceAlreadyExistsExceptionWhenSavingAssetWithDuplicateName() {
        Modality fixedModality = modality(UUID.randomUUID(), "LTN", YieldType.FIXED);
        Asset asset = asset(null, "Asset A", fixedModality);

        when(assetRepository.existsByName("Asset A")).thenReturn(true);

        assertThatThrownBy(() -> assetService.save(asset))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void shouldSaveValidFixedYieldAssetWithoutMarketIndex() {
        Modality fixedModality = modality(UUID.randomUUID(), "LTN", YieldType.FIXED);
        Asset asset = asset(null, "Asset A", fixedModality);
        asset.setInterestRate(new BigDecimal("0.05"));

        when(assetRepository.existsByName("Asset A")).thenReturn(false);
        when(modalityRepository.existsByName("LTN")).thenReturn(true);
        when(modalityService.findByName("LTN")).thenReturn(fixedModality);
        when(assetRepository.save(asset)).thenReturn(asset);

        Asset saved = assetService.save(asset);

        assertThat(saved).isEqualTo(asset);
    }

    @Test
    void shouldThrowMissingArgumentExceptionWhenFixedYieldAssetHasNoInterestRate() {
        Modality fixedModality = modality(UUID.randomUUID(), "LTN", YieldType.FIXED);
        Asset asset = asset(null, "Asset A", fixedModality);

        when(assetRepository.existsByName("Asset A")).thenReturn(false);
        when(modalityRepository.existsByName("LTN")).thenReturn(true);
        when(modalityService.findByName("LTN")).thenReturn(fixedModality);

        assertThatThrownBy(() -> assetService.save(asset))
                .isInstanceOf(MissingArgumentException.class);
    }

    @Test
    void shouldSaveValidFloatingYieldAssetWithMarketIndex() {
        Modality floatingModality = modality(UUID.randomUUID(), "LFT", YieldType.FLOATING);
        Asset asset = asset(null, "Asset B", floatingModality);
        asset.setMarketIndex(new MarketIndex(null, "SELIC"));
        asset.setIndexPercentage(new BigDecimal("1.0"));

        when(assetRepository.existsByName("Asset B")).thenReturn(false);
        when(modalityRepository.existsByName("LFT")).thenReturn(true);
        when(modalityService.findByName("LFT")).thenReturn(floatingModality);
        when(marketIndexRepository.findByName("SELIC")).thenReturn(Optional.of(new MarketIndex(UUID.randomUUID(), "SELIC")));
        when(assetRepository.save(asset)).thenReturn(asset);

        Asset saved = assetService.save(asset);

        assertThat(saved).isEqualTo(asset);
    }

    @Test
    void shouldThrowRelatedResourceNotFoundExceptionWhenFloatingYieldAssetHasNonexistentMarketIndex() {
        Modality floatingModality = modality(UUID.randomUUID(), "LFT", YieldType.FLOATING);
        Asset asset = asset(null, "Asset B", floatingModality);
        asset.setMarketIndex(new MarketIndex(null, "Nonexistent Index"));
        asset.setIndexPercentage(new BigDecimal("1.0"));

        when(assetRepository.existsByName("Asset B")).thenReturn(false);
        when(modalityRepository.existsByName("LFT")).thenReturn(true);
        when(modalityService.findByName("LFT")).thenReturn(floatingModality);
        when(marketIndexRepository.findByName("Nonexistent Index")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> assetService.save(asset))
                .isInstanceOf(RelatedResourceNotFoundException.class);
    }

    @Test
    void shouldThrowForbiddenArgumentExceptionWhenFixedYieldAssetHasMarketIndex() {
        Modality fixedModality = modality(UUID.randomUUID(), "LTN", YieldType.FIXED);
        Asset asset = asset(null, "Asset A", fixedModality);
        asset.setInterestRate(new BigDecimal("0.05"));
        asset.setMarketIndex(new MarketIndex(null, "SELIC"));

        when(assetRepository.existsByName("Asset A")).thenReturn(false);
        when(modalityRepository.existsByName("LTN")).thenReturn(true);
        when(modalityService.findByName("LTN")).thenReturn(fixedModality);

        assertThatThrownBy(() -> assetService.save(asset))
                .hasMessageContaining("market index must not be set");
    }
}
