package com.dalgona.zerozone;

import com.dalgona.zerozone.common.OnlyResponseString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/ping")
    public OnlyResponseString healthCheck(){
        return OnlyResponseString.of("pong");
    }
}
