package com.gui.VUTTR.controllers;

import com.gui.VUTTR.entities.Tool;
import com.gui.VUTTR.exceptions.NotFoundException;
import com.gui.VUTTR.model.ToolDto;
import com.gui.VUTTR.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class ToolController {
    public static final String TOOL_PATH = "/v1/tools";
    public static final String TOOL_PATH_ID = TOOL_PATH + "/{id}";

    private final ToolService toolService;


    @GetMapping(TOOL_PATH)
    public Page<ToolDto> handleGet(@RequestParam(required = false) Set<String> tags,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize){



        return toolService.listTools(tags, pageNumber, pageSize);
    }

    @PostMapping(TOOL_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody ToolDto toolDto){
        Optional<ToolDto> existingTool = toolService.getToolByTitle(toolDto.getTitle());
        if(existingTool.isPresent()){
            String message = "There is already a tool with id: " + existingTool.get().getId();
            return new ResponseEntity(message,HttpStatus.CONFLICT);
        }
        ToolDto savedTool = toolService.saveNewTool(toolDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", TOOL_PATH + "/" + savedTool.getId().toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PostMapping(TOOL_PATH_ID)
    public ResponseEntity handlePOSTwithId(@PathVariable Long id, @Validated @RequestBody ToolDto toolDto){

        ToolDto updatedTool = toolService.updateTool(id, toolDto)
                .orElseThrow(()-> new NotFoundException("No such element with id: " + id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", TOOL_PATH + "/" + updatedTool.getId().toString());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(TOOL_PATH_ID)
    public ResponseEntity handleGetById(@PathVariable Long id){
        ToolDto toolDto = toolService.getToolById(id)
                .orElseThrow(()-> new NotFoundException("There is no such element with id: "+ id));
        return new ResponseEntity<>(toolDto, HttpStatus.OK);
    }


    @DeleteMapping(TOOL_PATH_ID)
    public ResponseEntity handleDeleteById(@PathVariable Long id){
        if(!toolService.deleteById(id)){
            throw new NotFoundException("No such element with id: "+id);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
