package com.example.springmc;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DeeplResponse {

    @JsonProperty("translations")
    private List<DeeplResponse> resultList;

    @JsonProperty("detected_source_language")
    private String sourceLang;

    @JsonProperty("text")
    private String resultText;
}
