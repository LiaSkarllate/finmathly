package dev.liaskarllate.finmathly.modality.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.modality.dto.ModalityDTO;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchFilter;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchSortingField;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class FixedModalityControllerTest {
    @Autowired
    private ModalityControllerTester tester;

    @Test
    void shouldPerformCRUDFunctionality() throws Exception {
        final String modalityName = "LTN";
        final YieldType yieldType = YieldType.FIXED;
        final CapitalizationPeriod capitalizationPeriod = CapitalizationPeriod.HALF_YEARLY;
        final boolean supportsFlows = true;

        ModalityDTO toBeCreated = new ModalityDTO();
        toBeCreated.setName(modalityName);
        toBeCreated.setYieldType(yieldType);
        toBeCreated.setCapitalizationPeriod(capitalizationPeriod);
        toBeCreated.setSupportsFlows(supportsFlows);

        ModalityDTO created = tester.create(toBeCreated);
        tester.readById(created.getId(), created);

        final String updatedModalityName = "LTN Atualizado";
        final YieldType updatedYieldType = YieldType.FIXED;
        final CapitalizationPeriod updatedCapitalizationPeriod = CapitalizationPeriod.MONTHLY;
        final boolean updatedSupportsFlows = false;

        ModalityDTO toBeUpdated = new ModalityDTO();
        toBeUpdated.setName(updatedModalityName);
        toBeUpdated.setYieldType(updatedYieldType);
        toBeUpdated.setCapitalizationPeriod(updatedCapitalizationPeriod);
        toBeUpdated.setSupportsFlows(updatedSupportsFlows);

        ModalityDTO updated = tester.update(created.getId(), toBeUpdated);
        tester.readById(created.getId(), updated);

        tester.remove(created.getId());
    }

    @Test
    void shouldReturnFilteredSortedPagedResults() throws Exception {
        final String modalityNameA = "MOD-FIXED";
        final YieldType yieldTypeA = YieldType.FIXED;
        final CapitalizationPeriod capitalizationPeriodA = CapitalizationPeriod.HALF_YEARLY;
        final boolean supportsFlowsA = true;

        ModalityDTO toBeCreatedA = new ModalityDTO();
        toBeCreatedA.setName(modalityNameA);
        toBeCreatedA.setYieldType(yieldTypeA);
        toBeCreatedA.setCapitalizationPeriod(capitalizationPeriodA);
        toBeCreatedA.setSupportsFlows(supportsFlowsA);

        ModalityDTO createdA = tester.create(toBeCreatedA);
        tester.readById(createdA.getId(), createdA);

        final String modalityNameB = "MOD-FLOATING";
        final YieldType yieldTypeB = YieldType.FLOATING;
        final CapitalizationPeriod capitalizationPeriodB = CapitalizationPeriod.MONTHLY;
        final boolean supportsFlowsB = true;

        ModalityDTO toBeCreatedB = new ModalityDTO();
        toBeCreatedB.setName(modalityNameB);
        toBeCreatedB.setYieldType(yieldTypeB);
        toBeCreatedB.setCapitalizationPeriod(capitalizationPeriodB);
        toBeCreatedB.setSupportsFlows(supportsFlowsB);

        ModalityDTO createdB = tester.create(toBeCreatedB);
        tester.readById(createdB.getId(), createdB);

        ModalitySearchFilter searchFilter = new ModalitySearchFilter(
                null,
                YieldType.FIXED);

        tester.readFilteredSortedAndPaged(
                searchFilter,
                ModalitySearchSortingField.name,
                0,
                10,
                List.of(createdA));
    }
}
