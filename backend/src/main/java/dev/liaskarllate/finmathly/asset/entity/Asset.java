package dev.liaskarllate.finmathly.asset.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import dev.liaskarllate.finmathly.marketindex.entity.MarketIndex;
import dev.liaskarllate.finmathly.modality.entity.Modality;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asset", indexes = { @Index(name = "idx_asset_maturity_date", columnList = "maturity_date") })
public class Asset {

    @Id
    @Generated(event = EventType.INSERT)
    private UUID id;

    @Column(length = 25, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modality_id", nullable = false)
    private Modality modality;

    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    @Column(name = "interest_rate", precision = 7, scale = 4)
    private BigDecimal interestRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_index_id")
    private MarketIndex marketIndex;

    @Column(name = "index_percentage", precision = 7, scale = 4)
    private BigDecimal indexPercentage;

    @Column(name = "face_value", precision = 17, scale = 2)
    private BigDecimal faceValue;

    @Column(name = "created_at", updatable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @Generated(event = { EventType.INSERT, EventType.UPDATE })
    private OffsetDateTime updatedAt;

    public Asset(UUID id) {
        this.id = id;
    }

    public Asset(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean hasInterestRate() {
        return this.interestRate != null;
    }

    public boolean hasIndexPercentage() {
        return this.indexPercentage != null;
    }

    public boolean hasMarketIndex() {
        return this.marketIndex != null;
    }
}