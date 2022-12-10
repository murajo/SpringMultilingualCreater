package com.example.springmc;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class MessagesProperties {

    private String language;
    private String text;
    private String fileName;
    
}
