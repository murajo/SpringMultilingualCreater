package com.example.springmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SpringmcService {

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private DeeplRequest deeplRequest;

    @Autowired
    private SpringmcApiKeyManage repository;

    private String apiUrl = "https://api-free.deepl.com/v2/translate";

    public Boolean rootRedirectJudge() {
        return repository.checkRegisteredApiKey();
    }

    public Boolean registerRedirectJudge() {
        return repository.checkRegisteredApiKey();
    }

    public Boolean translateRedirectJudge() {
        return repository.checkRegisteredApiKey();
    }

    public Map translateMessageProperties(DeeplRequest deeplRequest) {

        Map<String, MessagesProperties> messagesPropertiesMap = new HashMap<String, MessagesProperties>();

        deeplRequest.setApiUrl(apiUrl);
        deeplRequest.setApiKey(repository.getApiKey());
        Map targetTextMap = splitTargetText(deeplRequest.getTargetText());

        for (int targetLangNum = 0; targetLangNum < deeplRequest.getTargetLang().length; targetLangNum++) {

            MessagesProperties messagesProperties = createMessagesProperties(targetTextMap, deeplRequest,
                    targetLangNum);
            messagesPropertiesMap.put(messagesProperties.getLanguage(), messagesProperties);
        }
        System.out.println(messagesPropertiesMap);
        return messagesPropertiesMap;

    }

    private Map splitTargetText(String targetText) {

        BufferedReader reader = new BufferedReader(new StringReader(targetText));
        String lineTargetText;
        Map<String, String> targetTextMap = new HashMap<String, String>();
        try {
            while ((lineTargetText = reader.readLine()) != null) {
                if(!lineTargetText.contains("=")) continue;
                String lineArray[] = lineTargetText.split("=");
                targetTextMap.put(lineArray[0], lineArray[1]);
            }
            return targetTextMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private DeeplResponse deeplRequestExec(RequestEntity request) {

        try {
            ResponseEntity<String> responseJson = restTemplate.exchange(request, String.class);
            DeeplResponse deeplResponse = mapper.readValue(responseJson.getBody(), DeeplResponse.class);
            return deeplResponse.getResultList().get(0);
        } catch (HttpClientErrorException | JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    private MessagesProperties createMessagesProperties(Map<String, String> targetTextMap, DeeplRequest deeplRequest,
            int targetLangNum) {

        String resultText = "";
        for (Map.Entry<String, String> entry : targetTextMap.entrySet()) {
            deeplRequest.setTargetText(entry.getValue());
            RequestEntity request = deeplRequest.requestCreate(targetLangNum);
            DeeplResponse response = deeplRequestExec(request);
            resultText = resultText + entry.getKey() + " = " + response.getResultText() + "\n";
        }

        MessagesProperties messagesProperties = new MessagesProperties();
        messagesProperties.setLanguage(deeplRequest.getTargetLang()[targetLangNum]);
        messagesProperties.setText(resultText);
        messagesProperties.setFileName("messages_" + messagesProperties.getLanguage() + ".properties");

        return messagesProperties;
    }

    public Boolean registerApiKey(String targetApiKey) {
        if (!testDeeplRequest(targetApiKey))
            return false;
        return repository.addRegisterApiKey(targetApiKey);

    }

    private Boolean testDeeplRequest(String targetApiKey) {

        String apiUrl = "https://api-free.deepl.com/v2/translate";
        String apiKey = targetApiKey;
        String sourceLang = "";
        String targetLang[] = { "JA" };
        String targetText = "";

        deeplRequest.setApiUrl(apiUrl);
        deeplRequest.setApiKey(apiKey);
        deeplRequest.setSourceLang(sourceLang);
        deeplRequest.setTargetLang(targetLang);
        deeplRequest.setTargetText(targetText);

        RequestEntity request = deeplRequest.requestCreate(0);

        return deeplRequestExec(request) != null;

    }

}
