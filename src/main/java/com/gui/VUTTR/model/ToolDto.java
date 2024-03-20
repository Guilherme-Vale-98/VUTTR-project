package com.gui.VUTTR.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gui.VUTTR.entities.Tag;
import com.gui.VUTTR.entities.Tool;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class ToolDto {
    private Long id;
    @NotBlank
    @NotNull
    @Size(max = 50, min = 3)
    private String title;
    @NotBlank
    @NotNull
    private String link;
    private String description;
    @NotEmpty
    private Set<String> tags;
    @JsonIgnore
    public Tool getTool(){
        return Tool.builder()
                .title(this.getTitle())
                .link(this.getLink())
                .tags(new HashSet<>())
                .description(this.getDescription())
                .build();
    }
}
