package com.dalgona.zerozone.common.response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/ping")
    public OnlyResponseString healthCheck(){
        return OnlyResponseString.of("pong");
    }
}
