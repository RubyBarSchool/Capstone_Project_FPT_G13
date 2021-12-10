package com.university.fpt.acf.config.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDeleteAll;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataReponseWebSocket {
    private HashMap<String,String> dataMessages;
}
