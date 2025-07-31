package dev.liaskarllate.finmathly.marketindex.controller;

import dev.liaskarllate.finmathly.marketindex.dto.MarketIndexDTO;
import dev.liaskarllate.finmathly.marketindex.query.MarketIndexSearchFilter;
import lombok.AllArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@AllArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
class MarketIndexControllerTest {
    private MarketIndexControllerTester tester;

    @Test
    void shouldPerformCRUDFunctionality() throws Exception {
        final String name = "Ibovespa";
        final String descrption = "Índice de ações da bolsa brasileira";

        MarketIndexDTO toBeCreated = new MarketIndexDTO();
        toBeCreated.setName(name);
        toBeCreated.setDescription(descrption);

        MarketIndexDTO created = tester.create(toBeCreated);
        tester.readById(created.getId(), created);

        final String updatedName = "Bovespa";
        final String updatedDescrption = "Índice atualizado de ações da bolsa brasileira";

        MarketIndexDTO toBeUpdated = new MarketIndexDTO();
        toBeUpdated.setName(updatedName);
        toBeUpdated.setDescription(updatedDescrption);

        MarketIndexDTO updated = tester.update(created.getId(),
                toBeUpdated);
        tester.readById(created.getId(), updated);

        tester.remove(created.getId());
    }

    @Test
    void shouldReturnFilteredSortedPagedResults() throws Exception {
        final String nameA = "Index A";
        final String descrptionA = "desc A";

        MarketIndexDTO toBeCreatedA = new MarketIndexDTO();
        toBeCreatedA.setName(nameA);
        toBeCreatedA.setDescription(descrptionA);

        MarketIndexDTO createdA = tester.create(toBeCreatedA);

        final String nameB = "Index B";
        final String descrptionB = "desc B";

        MarketIndexDTO toBeCreatedB = new MarketIndexDTO();
        toBeCreatedB.setName(nameB);
        toBeCreatedB.setDescription(descrptionB);

        tester.create(toBeCreatedB);

        final String nameC = "Index C";
        final String descrptionC = "desc C";

        MarketIndexDTO toBeCreatedC = new MarketIndexDTO();
        toBeCreatedC.setName(nameC);
        toBeCreatedC.setDescription(descrptionC);

        tester.create(toBeCreatedC);

        MarketIndexSearchFilter searchFilter = new MarketIndexSearchFilter(
                "Index A");

        tester.readFilteredSortedAndPaged(
                searchFilter,
                "name",
                0,
                10,
                List.of(createdA));
    }
}
