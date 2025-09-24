package dev.liaskarllate.finmathly.flow.controller;

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

import dev.liaskarllate.finmathly.flow.dto.FlowDTO;
import dev.liaskarllate.finmathly.flow.query.FlowSearchFilter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FlowControllerTester {
    private static final String BASE_PATH = "/flows";

    private final MockMvc mvc;
    private final ObjectMapper json;

    public FlowDTO create(FlowDTO toBeCreated) throws Exception {
        String responseBody = mvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeCreated)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlowDTO created = json.readValue(responseBody, FlowDTO.class);

        assertEquals(toBeCreated.getAssetId(), created.getAssetId());
        assertEquals(toBeCreated.getAssetName(), created.getAssetName());
        assertEquals(toBeCreated.getType(), created.getType());
        assertEquals(toBeCreated.getEventDate(), created.getEventDate());
        assertEquals(toBeCreated.getAmount(), created.getAmount());
        assertEquals(toBeCreated.getAmortizationPercentage(), created.getAmortizationPercentage());
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());

        return created;
    }

    public FlowDTO readById(UUID id, FlowDTO expected) throws Exception {
        String responseBody = mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlowDTO read = json.readValue(responseBody, FlowDTO.class);

        assertEquals(expected.getId(), read.getId());
        assertEquals(expected.getAssetId(), read.getAssetId());
        assertEquals(expected.getAssetName(), read.getAssetName());
        assertEquals(expected.getType(), read.getType());
        assertEquals(expected.getEventDate(), read.getEventDate());
        assertEquals(expected.getAmount(), read.getAmount());
        assertEquals(expected.getAmortizationPercentage(), read.getAmortizationPercentage());

        return read;
    }

    public FlowDTO update(UUID id, FlowDTO toBeUpdated) throws Exception {
        String responseBody = mvc.perform(put(BASE_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeUpdated)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        FlowDTO updated = json.readValue(responseBody, FlowDTO.class);

        assertEquals(id, updated.getId());
        assertEquals(toBeUpdated.getAssetId(), updated.getAssetId());
        assertEquals(toBeUpdated.getAssetName(), updated.getAssetName());
        assertEquals(toBeUpdated.getType(), updated.getType());
        assertEquals(toBeUpdated.getEventDate(), updated.getEventDate());
        assertEquals(toBeUpdated.getAmount(), updated.getAmount());
        assertEquals(toBeUpdated.getAmortizationPercentage(), updated.getAmortizationPercentage());

        return updated;
    }

    public void remove(UUID id) throws Exception {
        mvc.perform(delete(BASE_PATH + "/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isNotFound());
    }

    public void readFilteredSortedAndPaged(
            FlowSearchFilter filter,
            String by,
            int pageNumber,
            int pageSize,
            List<FlowDTO> expected) throws Exception {

        MockHttpServletRequestBuilder request = get(BASE_PATH)
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize));

        if (filter.getAssetName() != null) {
            request.param("assetName", filter.getAssetName());
        }
        if (filter.getAssetId() != null) {
            request.param("assetID", filter.getAssetId().toString());
        }
        if (filter.getType() != null) {
            request.param("type", filter.getType().name());
        }
        if (filter.getEventDate() != null) {
            request.param("eventDate", filter.getEventDate().toString());
        }
        if (by != null) {
            request.param("sort", by);
        }

        String responseBody = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<FlowDTO> read = Arrays.asList(json.readValue(
                responseBody,
                json.getTypeFactory().constructArrayType(FlowDTO.class)));

        assertEquals(expected.size(), read.size());
        assertEquals(expected, read);
    }
}