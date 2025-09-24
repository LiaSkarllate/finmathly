package dev.liaskarllate.finmathly.modality.controller;

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

import dev.liaskarllate.finmathly.modality.dto.ModalityDTO;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchFilter;
import dev.liaskarllate.finmathly.modality.query.ModalitySearchSortingField;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ModalityControllerTester {
    private static final String BASE_PATH = "/modalities";

    private final MockMvc mvc;
    private final ObjectMapper json;

    public ModalityDTO create(ModalityDTO toBeCreated) throws Exception {
        String responseBody = mvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeCreated)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ModalityDTO created = json.readValue(responseBody, ModalityDTO.class);

        assertEquals(toBeCreated.getName(), created.getName());
        assertEquals(toBeCreated.getYieldType(), created.getYieldType());
        assertEquals(toBeCreated.getCapitalizationPeriod(), created.getCapitalizationPeriod());
        assertEquals(toBeCreated.getSupportsFlows(), created.getSupportsFlows());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());

        return created;
    }

    public ModalityDTO readById(UUID id, ModalityDTO expected) throws Exception {
        String responseBody = mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ModalityDTO read = json.readValue(responseBody, ModalityDTO.class);

        assertEquals(expected.getId(), read.getId());
        assertEquals(expected.getName(), read.getName());
        assertEquals(expected.getYieldType(), read.getYieldType());
        assertEquals(expected.getCapitalizationPeriod(), read.getCapitalizationPeriod());
        assertEquals(expected.getSupportsFlows(), read.getSupportsFlows());

        return read;
    }

    public ModalityDTO update(UUID id, ModalityDTO toBeUpdated) throws Exception {
        String responseBody = mvc.perform(put(BASE_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeUpdated)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ModalityDTO updated = json.readValue(responseBody, ModalityDTO.class);

        assertEquals(id, updated.getId());
        assertEquals(toBeUpdated.getName(), updated.getName());
        assertEquals(toBeUpdated.getYieldType(), updated.getYieldType());
        assertEquals(toBeUpdated.getCapitalizationPeriod(), updated.getCapitalizationPeriod());
        assertEquals(toBeUpdated.getSupportsFlows(), updated.getSupportsFlows());

        return updated;
    }

    public void remove(UUID id) throws Exception {
        mvc.perform(delete(BASE_PATH + "/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isNotFound());
    }

    public void readFilteredSortedAndPaged(
            ModalitySearchFilter filter,
            ModalitySearchSortingField by,
            int pageNumber,
            int pageSize,
            List<ModalityDTO> expected) throws Exception {

        MockHttpServletRequestBuilder request = get(BASE_PATH)
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize));

        if (filter.getName() != null) {
            request.param("name", filter.getName());
        }
        if (filter.getYieldType() != null) {
            request.param("yieldType", filter.getYieldType().name());
        }
        if (by != null) {
            request.param("by", by.toString());
        }

        String responseBody = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ModalityDTO> read = Arrays.asList(json.readValue(
                responseBody,
                json.getTypeFactory().constructArrayType(ModalityDTO.class)));

        assertEquals(expected.size(), read.size());
        assertEquals(expected, read);
    }
}