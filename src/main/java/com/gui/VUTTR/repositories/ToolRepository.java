package com.gui.VUTTR.repositories;


import com.gui.VUTTR.entities.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Optional<Tool> findToolByTitle(String title);

    @Query(value = "SELECT t FROM Tool t JOIN t.tags a WHERE a.name IN ?1")
    Page<Tool> findByTags(Set<String> tags, Pageable pageable);
}
