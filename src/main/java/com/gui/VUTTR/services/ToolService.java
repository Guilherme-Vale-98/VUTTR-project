package com.gui.VUTTR.services;


import com.gui.VUTTR.model.ToolDto;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;


public interface ToolService {

    Page<ToolDto> listTools(Set<String> tags, Integer pageNumber, Integer pageSize);

    Optional<ToolDto> getToolById(Long id);
    Optional<ToolDto> getToolByTitle(String title);
    ToolDto saveNewTool(ToolDto tool);

    Optional<ToolDto> updateTool(Long id, ToolDto tool);

    Boolean deleteById(Long id);

}
