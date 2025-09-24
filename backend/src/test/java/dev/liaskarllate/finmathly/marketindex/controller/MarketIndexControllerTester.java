package dev.liaskarllate.finmathly.marketindex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.liaskarllate.finmathly.marketindex.dto.MarketIndexDTO;
import dev.liaskarllate.finmathly.marketindex.query.MarketIndexSearchFilter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MarketIndexControllerTester {
    private static final String BASE_PATH = "/market-indexes";

    private final MockMvc mvc;
    private final ObjectMapper json;

    public MarketIndexDTO create(MarketIndexDTO toBeCreated) throws Exception {
        String response = mvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeCreated)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MarketIndexDTO created = json.readValue(response, MarketIndexDTO.class);

        assertEquals(toBeCreated.getName(), created.getName());
        assertEquals(toBeCreated.getDescription(), created.getDescription());
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());

        return created;
    }

    public MarketIndexDTO readById(UUID id, MarketIndexDTO expected) throws Exception {
        String response = mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MarketIndexDTO read = json.readValue(response, MarketIndexDTO.class);

        assertEquals(expected.getId(), read.getId());
        assertEquals(expected.getName(), read.getName());
        assertEquals(expected.getDescription(), read.getDescription());

        return read;
    }

    public MarketIndexDTO update(UUID id, MarketIndexDTO toBeUpdated) throws Exception {
        String response = mvc.perform(put(BASE_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeUpdated)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MarketIndexDTO updated = json.readValue(response, MarketIndexDTO.class);

        assertEquals(id, updated.getId());
        assertEquals(toBeUpdated.getName(), updated.getName());
        assertEquals(toBeUpdated.getDescription(), updated.getDescription());

        return updated;
    }

    public void remove(UUID id) throws Exception {
        mvc.perform(delete(BASE_PATH + "/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isNotFound());
    }

    public void readFilteredSortedAndPaged(
            MarketIndexSearchFilter filter,
            String by,
            int pageNumber,
            int pageSize,
            List<MarketIndexDTO> expected) throws Exception {

        MockHttpServletRequestBuilder request = get(BASE_PATH)
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize));

        if (filter.getName() != null) {
            request.param("name", filter.getName());
        }
        if (by != null) {
            request.param("sort", by);
        }

        String response = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<MarketIndexDTO> read = Arrays.asList(json.readValue(
                response,
                json.getTypeFactory().constructArrayType(MarketIndexDTO.class)));

        assertEquals(expected.size(), read.size());
        assertEquals(expected, read);
    }
}
