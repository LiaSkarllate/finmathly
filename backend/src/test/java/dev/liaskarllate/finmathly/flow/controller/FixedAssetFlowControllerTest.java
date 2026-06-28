package dev.liaskarllate.finmathly.flow.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.liaskarllate.finmathly.asset.dto.AssetDTO;
import dev.liaskarllate.finmathly.modality.dto.ModalityDTO;
import dev.liaskarllate.finmathly.shared.enums.CapitalizationPeriod;
import dev.liaskarllate.finmathly.shared.enums.YieldType;
import dev.liaskarllate.finmathly.shared.enums.EventType;
import dev.liaskarllate.finmathly.flow.dto.FlowDTO;
import dev.liaskarllate.finmathly.flow.query.FlowSearchFilter;
import dev.liaskarllate.finmathly.asset.controller.AssetControllerTester;
import dev.liaskarllate.finmathly.modality.controller.ModalityControllerTester;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class FixedAssetFlowControllerTest {
    @Autowired
    private ModalityControllerTester modalityTester;
    @Autowired
    private AssetControllerTester assetTester;
    @Autowired
    private FlowControllerTester flowTester;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;

    @Test
    void shouldPerformCRUDFunctionality() throws Exception {
        final String modalityName = "LTN";
        final YieldType yieldType = YieldType.FIXED;
        final CapitalizationPeriod capPeriod = CapitalizationPeriod.HALF_YEARLY;
        final boolean supportsFlows = true;

        ModalityDTO modalityToBeCreated = new ModalityDTO();
        modalityToBeCreated.setName(modalityName);
        modalityToBeCreated.setYieldType(yieldType);
        modalityToBeCreated.setCapitalizationPeriod(capPeriod);
        modalityToBeCreated.setSupportsFlows(supportsFlows);

        ModalityDTO createdModality = modalityTester.create(modalityToBeCreated);
        modalityTester.readById(createdModality.getId(), createdModality);

        final String assetName = "Tesouro Prefixado 3 anos";
        final LocalDate maturity = LocalDate.now().plusYears(3);
        final BigDecimal rate = new BigDecimal("0.1348");
        final BigDecimal faceValue = new BigDecimal("723.56");

        AssetDTO assetToBeCreated = new AssetDTO();
        assetToBeCreated.setName(assetName);
        assetToBeCreated.setMaturityDate(maturity);
        assetToBeCreated.setInterestRate(rate);
        assetToBeCreated.setFaceValue(faceValue);
        assetToBeCreated.setModalityName(modalityName);

        AssetDTO createdAsset = assetTester.create(assetToBeCreated);
        assetTester.readById(createdAsset.getId(), createdAsset);

        final EventType flowType = EventType.INTEREST;
        final LocalDate eventDate = LocalDate.now().plusMonths(6);
        final BigDecimal amount = new BigDecimal("50.00");
        final BigDecimal amortizationPercentage = new BigDecimal("0.00");

        FlowDTO flowToBeCreated = new FlowDTO();
        flowToBeCreated.setAssetId(createdAsset.getId());
        flowToBeCreated.setAssetName(createdAsset.getName());
        flowToBeCreated.setType(flowType);
        flowToBeCreated.setEventDate(eventDate);
        flowToBeCreated.setAmount(amount);
        flowToBeCreated.setAmortizationPercentage(amortizationPercentage);

        FlowDTO createdFlow = flowTester.create(flowToBeCreated);
        flowTester.readById(createdFlow.getId(), createdFlow);

        final EventType updatedType = EventType.AMORTIZATION;
        final LocalDate updatedDate = LocalDate.now().plusYears(1);
        final BigDecimal updatedAmount = new BigDecimal("723.56");
        final BigDecimal updatedAmort = new BigDecimal("100");

        FlowDTO flowToBeUpdated = new FlowDTO();
        flowToBeUpdated.setAssetId(createdAsset.getId());
        flowToBeUpdated.setAssetName(createdAsset.getName());
        flowToBeUpdated.setType(updatedType);
        flowToBeUpdated.setEventDate(updatedDate);
        flowToBeUpdated.setAmount(updatedAmount);
        flowToBeUpdated.setAmortizationPercentage(updatedAmort);

        FlowDTO updatedFlow = flowTester.update(createdFlow.getId(), flowToBeUpdated);
        flowTester.readById(createdFlow.getId(), updatedFlow);

        flowTester.remove(createdFlow.getId());
    }

    @Test
    void shouldReturnFilteredSortedPagedResults() throws Exception {
        final String modalityName = "LTN";
        final YieldType yieldType = YieldType.FIXED;
        final CapitalizationPeriod capitalizationPeriod = CapitalizationPeriod.HALF_YEARLY;
        final boolean supportsFlows = true;

        ModalityDTO modalityToBeCreated = new ModalityDTO();
        modalityToBeCreated.setName(modalityName);
        modalityToBeCreated.setYieldType(yieldType);
        modalityToBeCreated.setCapitalizationPeriod(capitalizationPeriod);
        modalityToBeCreated.setSupportsFlows(supportsFlows);

        ModalityDTO createdModality = modalityTester.create(modalityToBeCreated);
        modalityTester.readById(createdModality.getId(), createdModality);

        final String assetName = "Asset for flows";
        final LocalDate maturity = LocalDate.now().plusYears(2);
        final BigDecimal rate = new BigDecimal("0.05");
        final BigDecimal faceValue = new BigDecimal("1000");

        AssetDTO assetToBeCreated = new AssetDTO();
        assetToBeCreated.setName(assetName);
        assetToBeCreated.setMaturityDate(maturity);
        assetToBeCreated.setInterestRate(rate);
        assetToBeCreated.setFaceValue(faceValue);
        assetToBeCreated.setModalityName(modalityName);

        AssetDTO createdAsset = assetTester.create(assetToBeCreated);

        final EventType flowTypeA = EventType.INTEREST;
        final LocalDate eventDateA = LocalDate.now().plusMonths(3);
        final BigDecimal amountA = new BigDecimal("25.00");
        final BigDecimal amortizationPercentageA = BigDecimal.ZERO;

        FlowDTO flowToBeCreatedA = new FlowDTO();
        flowToBeCreatedA.setAssetId(createdAsset.getId());
        flowToBeCreatedA.setAssetName(createdAsset.getName());
        flowToBeCreatedA.setType(flowTypeA);
        flowToBeCreatedA.setEventDate(eventDateA);
        flowToBeCreatedA.setAmount(amountA);
        flowToBeCreatedA.setAmortizationPercentage(amortizationPercentageA);

        FlowDTO flowCreatedA = flowTester.create(flowToBeCreatedA);

        final EventType flowTypeB = EventType.INTEREST;
        final LocalDate eventDateB = LocalDate.now().plusMonths(6);
        final BigDecimal amountB = new BigDecimal("25.00");
        final BigDecimal amortizationPercentageB = BigDecimal.ZERO;

        FlowDTO flowToBeCreatedB = new FlowDTO();
        flowToBeCreatedB.setAssetId(createdAsset.getId());
        flowToBeCreatedB.setAssetName(createdAsset.getName());
        flowToBeCreatedB.setType(flowTypeB);
        flowToBeCreatedB.setEventDate(eventDateB);
        flowToBeCreatedB.setAmount(amountB);
        flowToBeCreatedB.setAmortizationPercentage(amortizationPercentageB);

        FlowDTO flowCreatedB = flowTester.create(flowToBeCreatedB);

        FlowSearchFilter flowSearchFilter = new FlowSearchFilter(
                null,
                createdAsset.getId(),
                null,
                null);

        flowTester.readFilteredSortedAndPaged(
                flowSearchFilter,
                "eventDate",
                0,
                10,
                List.of(flowCreatedA, flowCreatedB));
    }

    @Test
    void shouldCreateFlowWhenReferencingAssetByNameOnly() throws Exception {
        ModalityDTO modalityToBeCreated = new ModalityDTO();
        modalityToBeCreated.setName("LTN Flow By Name Test");
        modalityToBeCreated.setYieldType(YieldType.FIXED);
        modalityToBeCreated.setCapitalizationPeriod(CapitalizationPeriod.HALF_YEARLY);
        modalityToBeCreated.setSupportsFlows(true);
        ModalityDTO createdModality = modalityTester.create(modalityToBeCreated);

        AssetDTO assetToBeCreated = new AssetDTO();
        assetToBeCreated.setName("Asset Flow By Name Test");
        assetToBeCreated.setMaturityDate(LocalDate.now().plusYears(1));
        assetToBeCreated.setInterestRate(new BigDecimal("0.05"));
        assetToBeCreated.setFaceValue(new BigDecimal("1000"));
        assetToBeCreated.setModalityName(createdModality.getName());
        AssetDTO createdAsset = assetTester.create(assetToBeCreated);

        FlowDTO flowToBeCreated = new FlowDTO();
        flowToBeCreated.setAssetName(createdAsset.getName());
        flowToBeCreated.setType(EventType.INTEREST);
        flowToBeCreated.setEventDate(LocalDate.now().plusMonths(6));
        flowToBeCreated.setAmount(new BigDecimal("50.00"));
        flowToBeCreated.setAmortizationPercentage(BigDecimal.ZERO);

        String responseBody = mvc.perform(post("/flows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(flowToBeCreated)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        FlowDTO created = json.readValue(responseBody, FlowDTO.class);

        assertEquals(createdAsset.getId(), created.getAssetId());
        assertEquals(createdAsset.getName(), created.getAssetName());
    }

    @Test
    void shouldReturnNotFoundWhenCreatingFlowForNonexistentAsset() throws Exception {
        FlowDTO flowToBeCreated = new FlowDTO();
        flowToBeCreated.setAssetName("Nonexistent Asset");
        flowToBeCreated.setType(EventType.INTEREST);
        flowToBeCreated.setEventDate(LocalDate.now().plusMonths(6));
        flowToBeCreated.setAmount(new BigDecimal("50.00"));
        flowToBeCreated.setAmortizationPercentage(BigDecimal.ZERO);

        mvc.perform(post("/flows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(flowToBeCreated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonexistentFlow() throws Exception {
        ModalityDTO modalityToBeCreated = new ModalityDTO();
        modalityToBeCreated.setName("LTN Flow Update Test");
        modalityToBeCreated.setYieldType(YieldType.FIXED);
        modalityToBeCreated.setCapitalizationPeriod(CapitalizationPeriod.HALF_YEARLY);
        modalityToBeCreated.setSupportsFlows(true);
        ModalityDTO createdModality = modalityTester.create(modalityToBeCreated);

        AssetDTO assetToBeCreated = new AssetDTO();
        assetToBeCreated.setName("Asset Flow Update Test");
        assetToBeCreated.setMaturityDate(LocalDate.now().plusYears(1));
        assetToBeCreated.setInterestRate(new BigDecimal("0.05"));
        assetToBeCreated.setFaceValue(new BigDecimal("1000"));
        assetToBeCreated.setModalityName(createdModality.getName());
        AssetDTO createdAsset = assetTester.create(assetToBeCreated);

        FlowDTO flowToBeUpdated = new FlowDTO();
        flowToBeUpdated.setAssetId(createdAsset.getId());
        flowToBeUpdated.setAssetName(createdAsset.getName());
        flowToBeUpdated.setType(EventType.INTEREST);
        flowToBeUpdated.setEventDate(LocalDate.now().plusMonths(6));
        flowToBeUpdated.setAmount(new BigDecimal("50.00"));
        flowToBeUpdated.setAmortizationPercentage(BigDecimal.ZERO);

        mvc.perform(put("/flows/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(flowToBeUpdated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistentFlow() throws Exception {
        mvc.perform(delete("/flows/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}