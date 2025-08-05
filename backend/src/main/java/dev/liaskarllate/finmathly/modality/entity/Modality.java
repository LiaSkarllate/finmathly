package dev.liaskarllate.finmathly.modality.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "modality")
public class Modality {
    @Id
    @Generated(event = EventType.INSERT)
    private UUID id;

    @Column(length = 25, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "yield_type", nullable = false)
    private YieldType yieldType;

    @Enumerated(EnumType.STRING)
    @Column(name = "capitalization_period", nullable = false)
    private CapitalizationPeriod capitalizationPeriod;

    @Column(name = "supports_flows", nullable = false)
    private Boolean supportsFlows = false;

    @Column(name = "created_at", updatable = false)
    @Generated(event = EventType.INSERT)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @Generated(event = { EventType.INSERT, EventType.UPDATE })
    private OffsetDateTime updatedAt;

    public Modality(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean hasName() {
        return this.getName() != null && !this.getName().isBlank();
    }

    public boolean hasId() {
        return this.getId() != null;
    }
}
