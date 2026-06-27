package dev.liaskarllate.finmathly.marketindex.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.liaskarllate.finmathly.marketindex.dto.MarketIndexDTO;
import dev.liaskarllate.finmathly.marketindex.query.MarketIndexSearchFilter;

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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class MarketIndexControllerTest {
    @Autowired
    private MarketIndexControllerTester tester;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper json;

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

    @Test
    void shouldReturnConflictWhenCreatingMarketIndexWithDuplicateName() throws Exception {
        MarketIndexDTO toBeCreated = new MarketIndexDTO();
        toBeCreated.setName("Duplicate Index");
        toBeCreated.setDescription("desc");

        tester.create(toBeCreated);

        mvc.perform(post("/market-indexes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeCreated)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonexistentMarketIndex() throws Exception {
        MarketIndexDTO toBeUpdated = new MarketIndexDTO();
        toBeUpdated.setName("Some Index");
        toBeUpdated.setDescription("desc");

        mvc.perform(put("/market-indexes/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeUpdated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonexistentMarketIndex() throws Exception {
        mvc.perform(delete("/market-indexes/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
