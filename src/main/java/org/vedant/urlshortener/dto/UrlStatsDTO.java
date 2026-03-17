package org.vedant.urlshortener.dto;

import org.vedant.urlshortener.model.ClickEvent;

import java.util.List;

public record UrlStatsDTO(String shortCode, String originalUrl, Long totalClicks, Long uniqueVisitors, List<ClickEvent> clickStats) {}
