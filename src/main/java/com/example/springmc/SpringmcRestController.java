package com.example.springmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringmcRestController {
    @Autowired
    private SpringmcService service;

    @PostMapping("/register/apikey")
    public String registerExec(@RequestParam(name = "apiKey") String targetApiKey) {

        return service.registerApiKey(targetApiKey) ? "success" : "failer";
    }

    @PostMapping("/translate/messageproperties")
    public Map translateExec(@RequestBody DeeplRequest deeplRequest) {
        return service.translateMessageProperties(deeplRequest);
    }
}
