package org.vedant.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vedant.urlshortener.model.UrlMapping;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    // Fetch Mapping by shortcode:
    Optional<UrlMapping> findByShortCode(String shortCode);

    // Delete Mapping by shortcode:
    void deleteByShortCode(String shortCode);
}
