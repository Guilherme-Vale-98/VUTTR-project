package com.gui.VUTTR.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gui.VUTTR.model.ToolDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 50, min = 3)
    private String title;
    @NotNull
    @NotBlank
    private String link;

    private String description;

    @NotEmpty
    @ManyToMany
    @Builder.Default
    @JoinTable(name = "tool_tag",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public ToolDto getToolDto(){
        return ToolDto.builder()
                .id(this.id)
                .title(this.getTitle())
                .link(this.getLink())
                .description(this.getDescription())
                .tags(this.getTagsName())
                .build();
    }

    private Set<String> getTagsName() {
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }

    public void addTag(Tag tag){
        this.getTags().add(tag);
        tag.getTools().add(this);
    }

    public void removeTag(Tag tag){
        this.getTags().remove(tag);
        tag.getTools().remove(this);
    }
}