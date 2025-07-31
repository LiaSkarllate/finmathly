package dev.liaskarllate.finmathly.flow.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import dev.liaskarllate.finmathly.asset.entity.Asset;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "flow", indexes = {@Index(name = "idx_flow_event_date", columnList = "event_date")})
public class Flow {
    @Id
    @Generated(event = EventType.INSERT)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private dev.liaskarllate.finmathly.shared.enums.EventType type;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(precision = 17, scale = 2)
    private BigDecimal amount;

    @Column(name = "amortization_percentage", precision = 7, scale = 4)
    private BigDecimal amortizationPercentage;

    @Column(name = "created_at", updatable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @Generated(event = {EventType.INSERT, EventType.UPDATE})
    private OffsetDateTime updatedAt;
}
