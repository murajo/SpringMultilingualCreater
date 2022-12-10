package com.example.springmc;

import java.net.URI;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.Data;

@Data
@Service
public class DeeplRequest {
    private String targetText;
    private String targetLang[];
    private String sourceLang;
    private String apiKey;
    private String apiUrl;

    public RequestEntity requestCreate(int targetLangNum){
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("text", targetText);
		map.add("target_lang", targetLang[targetLangNum]);
		map.add("source_lang", sourceLang);
		map.add("auth_key", apiKey);

        return RequestEntity.post(URI.create(apiUrl)).body(map);
    }
}
