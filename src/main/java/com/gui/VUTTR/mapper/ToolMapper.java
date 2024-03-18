package com.gui.VUTTR.mapper;

import com.gui.VUTTR.entities.Tool;
import com.gui.VUTTR.model.ToolDto;
import org.mapstruct.Mapper;

@Mapper
public interface ToolMapper {
    Tool toolDtoToTool(ToolDto toolDto);

    ToolDto toolToToolDto(Tool tool);

}
