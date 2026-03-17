package org.vedant.urlshortener.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.vedant.urlshortener.dto.UrlStatsDTO;
import org.vedant.urlshortener.model.ClickEvent;
import org.vedant.urlshortener.model.UrlMapping;
import org.vedant.urlshortener.repository.ClickEventRepository;
import org.vedant.urlshortener.repository.UrlMappingRepository;
import org.vedant.urlshortener.util.Base62;
import org.vedant.urlshortener.util.RandomUtil;

import java.util.List;

@Service
public class UrlService {

    private final UrlMappingRepository repo;
    private final ClickEventRepository eventRepo;

    public UrlService(UrlMappingRepository repo, ClickEventRepository eventRepo){
        this.repo = repo;
        this.eventRepo = eventRepo;
    }

    @Transactional
    public UrlMapping create(String originalUrl) {
        // Save initial record to obtain ID
        UrlMapping mapping = new UrlMapping();
            mapping.setOriginalUrl(originalUrl);

        mapping = repo.save(mapping);

        // Generate shortCode deterministically from id with some randomization:
        long baseId = mapping.getId() * 7 + 5;
        String code = Base62.encode(baseId);
        code += RandomUtil.randomChar(3);
        mapping.setShortCode(code);

        return repo.save(mapping);
    }

    public UrlMapping findByShortCode(String shortCode){
        return repo.findByShortCode(shortCode).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "short code not found: " + shortCode));
    }

    public String getOriginalUrl(String shortCode){
        UrlMapping m = repo.findByShortCode(shortCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return m.getOriginalUrl();
    }

    @Async
    @Transactional
    public void captureHit(String shortCode, String ipAddress, String userAgent){
        try {
            ClickEvent event = new ClickEvent();
            event.setShortCode(shortCode);
            event.setIpAddress(ipAddress);

            String ua = userAgent.toLowerCase();

            // Identify Platform:
            if(ua.contains("android")) event.setPlatform("Android");
            else if(ua.contains("iphone") || ua.contains("ipad")) event.setPlatform("iOS");
            else if(ua.contains("windows")) event.setPlatform("Windows");
            else if(ua.contains("mac")) event.setPlatform("MacOS");
            else if(ua.contains("linux")) event.setPlatform("Linux");
            else event.setPlatform("Other/Robot");

            // Identify Browser:
            if(ua.contains("edg")) event.setBrowser("Edge");
            else if(ua.contains("chrome")) event.setBrowser("Chrome");
            else if(ua.contains("safari")) event.setBrowser("Safari");
            else if(ua.contains("firefox")) event.setBrowser("Firefox");
            else event.setBrowser("Other");

            eventRepo.save(event);
        }catch (Exception e){
            System.err.println("Failed to get telemetry data: " + e.getMessage());
        }

        repo.findByShortCode(shortCode).ifPresent(m -> {
            m.setAccessCount(m.getAccessCount() + 1);
            repo.save(m);
        });
    }

    public UrlStatsDTO getStatData(String shortCode){
        UrlMapping m = repo.findByShortCode(shortCode).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<ClickEvent> c = eventRepo.findAllByShortCode(shortCode);
        Long uniqueVisitors = c.stream().map(ClickEvent::getIpAddress).distinct().count();

        return new UrlStatsDTO(m.getShortCode(),m.getOriginalUrl(),m.getAccessCount(),uniqueVisitors,c);
    }

    @Transactional
    public UrlMapping updateOriginalUrl(String shortCode, String newOriginalUrl){
        UrlMapping m = findByShortCode(shortCode);
        m.setOriginalUrl(newOriginalUrl);
        return repo.save(m);
    }

    @Transactional
    public void deleteByShortCode(String shortCode){
        UrlMapping m = findByShortCode(shortCode);
        repo.delete(m);
    }

}
