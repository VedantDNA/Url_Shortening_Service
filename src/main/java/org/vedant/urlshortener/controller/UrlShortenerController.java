package org.vedant.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vedant.urlshortener.dto.UrlStatsDTO;
import org.vedant.urlshortener.model.ClickEvent;
import org.vedant.urlshortener.model.UrlMapping;
import org.vedant.urlshortener.service.UrlService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

    private final UrlService service;

    public UrlShortenerController(UrlService service){
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<String> greet(){
        return ResponseEntity.ok("Welcome to Url Shortener Application !!!");
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String originalUrl){
        UrlMapping m = service.create(originalUrl);
        return ResponseEntity.ok(m.getShortCode());
    }

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response, HttpServletRequest request) throws IOException{
        String url = service.getOriginalUrl(shortCode);

        // user agent for connection metadata:
        String userAgent = request.getHeader("User-Agent");

        /*
        Note : using X-Forwarded-For to get ip of distributed system as we might not be able to fetch the actual ip.
        if empty we use getRemoteAddr() for client device ip.
        */
        String ipAddr = request.getHeader("X-Forwarded-For");
        if(ipAddr == null || ipAddr.isEmpty()){
            ipAddr = request.getRemoteAddr();
        }

        service.captureHit(shortCode, ipAddr, userAgent); 
        response.sendRedirect(url);
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
    public ResponseEntity<UrlStatsDTO> getStats(@PathVariable String shortCode){
        UrlStatsDTO data = service.getStatData(shortCode);
        return ResponseEntity.ok(data);
    }

}
