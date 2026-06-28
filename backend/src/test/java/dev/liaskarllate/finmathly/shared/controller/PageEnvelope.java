package dev.liaskarllate.finmathly.shared.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageEnvelope<T> {
    public List<T> content;
}
