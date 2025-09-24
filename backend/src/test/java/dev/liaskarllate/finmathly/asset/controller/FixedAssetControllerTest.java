package dev.liaskarllate.finmathly.asset.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dev.liaskarllate.finmathly.asset.dto.AssetDTO;
import dev.liaskarllate.finmathly.asset.query.AssetSearchFilter;
import dev.liaskarllate.finmathly.asset.query.AssetSearchSortingField;
import dev.liaskarllate.finmathly.modality.controller.ModalityControllerTester;
import dev.liaskarllate.finmathly.modality.dto.ModalityDTO;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class FixedAssetControllerTest {
    @Autowired
    private AssetControllerTester assetTester;
    @Autowired
    private ModalityControllerTester modalityTester;

    @Test
    void shouldPerformCRUDFunctionality() throws Exception {
        final String modalityName = "LTN";
        final YieldType yieldType = YieldType.FIXED;
        final CapitalizationPeriod capitalizationPeriod = CapitalizationPeriod.HALF_YEARLY;
        final boolean supportsFlows = false;

        ModalityDTO modalityToBeCreated = new ModalityDTO();
        modalityToBeCreated.setName(modalityName);
        modalityToBeCreated.setYieldType(yieldType);
        modalityToBeCreated.setCapitalizationPeriod(capitalizationPeriod);
        modalityToBeCreated.setSupportsFlows(supportsFlows);

        ModalityDTO createdModality = modalityTester.create(modalityToBeCreated);
        modalityTester.readById(createdModality.getId(), createdModality);

        final String name = "Tesouro Prefixado 3 anos";
        final LocalDate maturityDate = LocalDate.now().plusYears(3);
        final BigDecimal interestRate = new BigDecimal("0.1348");
        final BigDecimal faceValue = new BigDecimal("723.56");

        AssetDTO assetToBeCreated = new AssetDTO();
        assetToBeCreated.setName(name);
        assetToBeCreated.setMaturityDate(maturityDate);
        assetToBeCreated.setInterestRate(interestRate);
        assetToBeCreated.setFaceValue(faceValue);
        assetToBeCreated.setModalityName(modalityName);

        final String updatedName = "Tesouro Prefixado 2 anos";
        final LocalDate updatedMaturityDate = LocalDate.now().plusYears(2);
        final BigDecimal updatedInterestRate = new BigDecimal("0.1254");
        final BigDecimal updatedFaceValue = new BigDecimal("457.89");

        AssetDTO assetToBeUpdated = new AssetDTO();
        assetToBeUpdated.setName(updatedName);
        assetToBeUpdated.setMaturityDate(updatedMaturityDate);
        assetToBeUpdated.setInterestRate(updatedInterestRate);
        assetToBeUpdated.setFaceValue(updatedFaceValue);

        AssetDTO assetCreated = assetTester.create(assetToBeCreated);
        assetTester.readById(assetCreated.getId(), assetCreated);
        AssetDTO assetUpdated = assetTester.update(assetCreated.getId(), assetToBeUpdated);
        assetTester.readById(assetCreated.getId(), assetUpdated);
        assetTester.remove(assetCreated.getId());
    }

    @Test
    void shouldReturnFilteredSortedPagedResults() throws Exception {
        final String modalityName = "LTN";
        final YieldType yieldType = YieldType.FIXED;
        final CapitalizationPeriod capitalizationPeriod = CapitalizationPeriod.HALF_YEARLY;
        final boolean supportsFlows = false;

        ModalityDTO modalityToBeCreated = new ModalityDTO();
        modalityToBeCreated.setName(modalityName);
        modalityToBeCreated.setYieldType(yieldType);
        modalityToBeCreated.setCapitalizationPeriod(capitalizationPeriod);
        modalityToBeCreated.setSupportsFlows(supportsFlows);

        ModalityDTO createdModality = modalityTester.create(modalityToBeCreated);
        modalityTester.readById(createdModality.getId(), createdModality);

        final String nameA = "Asset A";
        final LocalDate maturityDateA = LocalDate.now().plusYears(1);
        final BigDecimal interestRateA = new BigDecimal("0.05");
        final BigDecimal faceValueA = new BigDecimal("1000");

        AssetDTO assetToBeCreatedA = new AssetDTO();
        assetToBeCreatedA.setName(nameA);
        assetToBeCreatedA.setMaturityDate(maturityDateA);
        assetToBeCreatedA.setInterestRate(interestRateA);
        assetToBeCreatedA.setFaceValue(faceValueA);
        assetToBeCreatedA.setModalityName(modalityName);

        AssetDTO createdA = assetTester.create(assetToBeCreatedA);
        assetTester.readById(createdA.getId(), createdA);

        final String nameB = "Asset B";
        final LocalDate maturityDateB = LocalDate.now().plusYears(2);
        final BigDecimal interestRateB = new BigDecimal("0.06");
        final BigDecimal faceValueB = new BigDecimal("2000");

        AssetDTO assetToBeCreatedB = new AssetDTO();
        assetToBeCreatedB.setName(nameB);
        assetToBeCreatedB.setMaturityDate(maturityDateB);
        assetToBeCreatedB.setInterestRate(interestRateB);
        assetToBeCreatedB.setFaceValue(faceValueB);
        assetToBeCreatedB.setModalityName(modalityName);

        AssetDTO createdB = assetTester.create(assetToBeCreatedB);
        assetTester.readById(createdB.getId(), createdB);

        AssetSearchFilter assetSearchFilter = new AssetSearchFilter(
                null,
                createdModality.getId(),
                null,
                null,
                null,
                null);

        assetTester.readFilteredSortedAndPaged(
                assetSearchFilter,
                AssetSearchSortingField.name,
                0,
                10,
                List.of(createdA, createdB));
    }
}