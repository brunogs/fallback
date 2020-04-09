package br.com.example.fallback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Collections.singletonMap;

@Controller
@RequestMapping("legacy-billing")
public class FallbackPaymentController {

    @GetMapping("/fallback-payments/{resource_id}/details")
    public ResponseEntity getPaymentDetails(@PathVariable("resource_id") String resourceId) {
        return ResponseEntity.ok(singletonMap("Hello", resourceId));
    }

}
