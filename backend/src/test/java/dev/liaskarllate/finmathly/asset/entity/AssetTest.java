package dev.liaskarllate.finmathly.asset.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;

class AssetTest {
    @Test
    void shouldReportNoMarketIndexWhenMarketIndexIsNull() {
        Asset asset = new Asset();
        asset.setMarketIndex(null);

        assertThat(asset.hasMarketIndex()).isFalse();
    }

    @Test
    void shouldReportNoMarketIndexWhenMarketIndexHasNeitherIdNorName() {
        Asset asset = new Asset();
        asset.setMarketIndex(new MarketIndex(null, null));

        assertThat(asset.hasMarketIndex()).isFalse();
    }

    @Test
    void shouldReportMarketIndexPresentWhenMarketIndexHasId() {
        Asset asset = new Asset();
        asset.setMarketIndex(new MarketIndex(UUID.randomUUID(), null));

        assertThat(asset.hasMarketIndex()).isTrue();
    }

    @Test
    void shouldReportMarketIndexPresentWhenMarketIndexHasName() {
        Asset asset = new Asset();
        asset.setMarketIndex(new MarketIndex(null, "IPCA"));

        assertThat(asset.hasMarketIndex()).isTrue();
    }
}
