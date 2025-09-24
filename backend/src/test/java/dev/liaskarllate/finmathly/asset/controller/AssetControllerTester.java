package dev.liaskarllate.finmathly.asset.controller;

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

import dev.liaskarllate.finmathly.asset.dto.AssetDTO;
import dev.liaskarllate.finmathly.asset.query.AssetSearchFilter;
import dev.liaskarllate.finmathly.asset.query.AssetSearchSortingField;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AssetControllerTester {
    private static final String BASE_PATH = "/assets";

    private final MockMvc mvc;
    private final ObjectMapper json;

    public AssetDTO create(AssetDTO toBeCreated) throws Exception {
        String responseBody = mvc.perform(post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeCreated)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AssetDTO created = json.readValue(responseBody, AssetDTO.class);

        assertEquals(toBeCreated.getName(), created.getName());
        assertEquals(toBeCreated.getMaturityDate(), created.getMaturityDate());
        assertEquals(toBeCreated.getInterestRate(), created.getInterestRate());
        assertEquals(toBeCreated.getFaceValue(), created.getFaceValue());
        assertEquals(toBeCreated.getModalityName(), created.getModalityName());

        assertNotNull(created.getId());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());

        return created;
    }

    public AssetDTO readById(UUID id, AssetDTO expected) throws Exception {
        String responseBody = mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AssetDTO read = json.readValue(responseBody, AssetDTO.class);

        assertEquals(expected.getId(), read.getId());
        assertEquals(expected.getName(), read.getName());
        assertEquals(expected.getMaturityDate(), read.getMaturityDate());
        assertEquals(expected.getInterestRate(), read.getInterestRate());
        assertEquals(expected.getFaceValue(), read.getFaceValue());
        assertEquals(expected.getModalityName(), read.getModalityName());

        return read;
    }

    public AssetDTO update(UUID id, AssetDTO toBeUpdated) throws Exception {
        String responseBody = mvc.perform(put(BASE_PATH + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.writeValueAsString(toBeUpdated)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AssetDTO updated = json.readValue(responseBody, AssetDTO.class);

        assertEquals(id, updated.getId());
        assertEquals(toBeUpdated.getName(), updated.getName());
        assertEquals(toBeUpdated.getMaturityDate(), updated.getMaturityDate());
        assertEquals(toBeUpdated.getInterestRate(), updated.getInterestRate());
        assertEquals(toBeUpdated.getFaceValue(), updated.getFaceValue());

        return updated;
    }

    public void remove(UUID id) throws Exception {
        mvc.perform(delete(BASE_PATH + "/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get(BASE_PATH + "/" + id))
                .andExpect(status().isNotFound());
    }

    public void readFilteredSortedAndPaged(
            AssetSearchFilter filter,
            AssetSearchSortingField by,
            int pageNumber,
            int pageSize,
            List<AssetDTO> expected) throws Exception {

        MockHttpServletRequestBuilder request = get(BASE_PATH)
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize));

        if (filter.getName() != null) {
            request.param("name", filter.getName());
        }
        if (filter.getModalityId() != null) {
            request.param("modalityId", filter.getModalityId().toString());
        }
        if (filter.getModalityName() != null) {
            request.param("modalityName", filter.getModalityName());
        }
        if (filter.getMaturityDate() != null) {
            request.param("maturityDate", filter.getMaturityDate().toString());
        }
        if (filter.getMarketIndexId() != null) {
            request.param("marketIndexId", filter.getMarketIndexId().toString());
        }
        if (filter.getMarketIndexName() != null) {
            request.param("marketIndexName", filter.getMarketIndexName());
        }
        if (by != null) {
            request.param("by", by.toString());
        }

        String responseBody = mvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        List<AssetDTO> read = Arrays.asList(json.readValue(
                responseBody,
                json.getTypeFactory().constructArrayType(AssetDTO.class)));

        assertEquals(expected.size(), read.size());
        assertEquals(expected, read);
    }
}