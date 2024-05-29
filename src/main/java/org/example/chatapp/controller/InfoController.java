package org.example.chatapp.controller;

import org.example.chatapp.dto.AppInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/info")
public class InfoController {
    @GetMapping
    public ResponseEntity<AppInfo> open(){
        return ResponseEntity.ok(AppInfo.builder()
                .appName("chat-app-service")
                .developers("Ayesh Vininda & Dilusha Madushan")
                .description("This app secured by WS02 Asgardeo")
                .build());
    }
}
