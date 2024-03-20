package com.gui.VUTTR.services;

import com.gui.VUTTR.entities.Tag;
import com.gui.VUTTR.entities.Tool;
import com.gui.VUTTR.exceptions.NotFoundException;
import com.gui.VUTTR.repositories.TagRepository;
import org.springframework.data.domain.Pageable;
import com.gui.VUTTR.model.ToolDto;
import com.gui.VUTTR.repositories.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService{

    private final ToolRepository toolRepository;
    private final TagRepository tagRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public Page<ToolDto> listTools(Set<String> tags, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Tool> toolPage;
        if(tags != null) {
            toolPage = listToolsByTags(tags, pageRequest);
        } else {
            toolPage = toolRepository.findAll(pageRequest);
        }
        return toolPage.map(Tool::getToolDto);
    }

    private Page<Tool> listToolsByTags(Set<String> tags, Pageable pageable) {
        return toolRepository.findByTags(tags, pageable);
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("title"));

        return PageRequest.of(queryPageNumber, queryPageSize,sort);
    }

    @Override
    public Optional<ToolDto> getToolById(Long id) {
        if(toolRepository.findById(id).isPresent()){
            return Optional.of(toolRepository.findById(id).get().getToolDto());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ToolDto> getToolByTitle(String title) {
        if(toolRepository.findToolByTitle(title).isPresent()){
            return Optional.of(toolRepository.findToolByTitle(title).get().getToolDto());
        }
        return Optional.empty();
    }

    @Override
    public ToolDto saveNewTool(ToolDto toolDto) {
        Set<Tag> tags = saveTags(toolDto.getTags());
        Tool tool = toolDto.getTool();
        for (Tag tag : tags){
            tool.addTag(tag);
        }
        return toolRepository.save(tool).getToolDto();
    }

    private Set<Tag> saveTags(Set<String> tags) {
        Set<Tag> newTags = new HashSet<>();
        for (String tag : tags){
            if(tagRepository.findByName(tag).isPresent()){
                newTags.add(tagRepository.findByName(tag).get());
                continue;
            }
            Tag savedTag = tagRepository.save(new Tag(null, tag, new HashSet<>()));
            newTags.add(savedTag);
        }
        return newTags;
    }


    @Override
    public Optional<ToolDto> updateTool(Long id,ToolDto toolDto) {
        Optional<Tool> existingTool = toolRepository.findById(id);

        if(existingTool.isPresent()){
            existingTool.get().setId(id);
            existingTool.get().setTitle(toolDto.getTitle());
            existingTool.get().setLink(toolDto.getLink());
            existingTool.get().setDescription(toolDto.getDescription());
            existingTool.get().setTags(saveTags(toolDto.getTags()));
            toolRepository.save(existingTool.get());
            return Optional.of(existingTool.get().getToolDto());
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(Long id) {
        if (toolRepository.existsById(id)) {
            toolRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
