package org.vedant.urlshortener.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vedant.urlshortener.model.UrlMapping;
import org.vedant.urlshortener.service.UrlService;

import java.io.IOException;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

    private final UrlService service;

    public UrlShortenerController(UrlService service){
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String originalUrl){
        UrlMapping m = service.create(originalUrl);
        return ResponseEntity.ok(m.getShortCode());
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException{
        UrlMapping m = service.incrementAndGet(shortCode);
        response.sendRedirect(m.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<UrlMapping> updateUrl(@PathVariable String shortCode, @RequestParam String newUrl){
        return ResponseEntity.ok(service.updateOriginalUrl(shortCode, newUrl));
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> delete(@PathVariable String shortCode){
        service.deleteByShortCode(shortCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getstats/{shortCode}")
    public ResponseEntity<UrlMapping> getStats(@PathVariable String shortCode){
        UrlMapping m = service.findByShortCode(shortCode);
        return ResponseEntity.ok(m);
    }

}
