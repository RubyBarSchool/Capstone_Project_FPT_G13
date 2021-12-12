package com.university.fpt.acf.config.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String username;
    private String usernameCreate;
    private String content;
    private String type = "success";
    private String path;
    private Boolean read = false;
    private LocalDateTime localDateTime = LocalDateTime.now();
}
