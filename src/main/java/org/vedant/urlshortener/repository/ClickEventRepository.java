package org.vedant.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedant.urlshortener.model.ClickEvent;
import java.util.List;


@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent,Long> {

    List<ClickEvent> findAllByShortCode(String shortCode);
}
