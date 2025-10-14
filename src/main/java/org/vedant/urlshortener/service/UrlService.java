package org.vedant.urlshortener.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.vedant.urlshortener.model.UrlMapping;
import org.vedant.urlshortener.repository.UrlMappingRepository;
import org.vedant.urlshortener.util.Base62;
import org.vedant.urlshortener.util.RandomUtil;

@Service
public class UrlService {

    private final UrlMappingRepository repo;

    public UrlService(UrlMappingRepository repo){
        this.repo = repo;
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

    @Transactional
    public UrlMapping incrementAndGet(String shortCode){
        UrlMapping m = findByShortCode(shortCode);
        m.setAccessCount(m.getAccessCount() + 1);
        return repo.save(m);
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
