package com.gui.VUTTR.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ToolDto {
    private int id;
    @NotBlank
    @NotNull
    @Size(max = 50, min = 3)
    private String name;
    @NotBlank
    @NotNull
    private String link;
    private String description;
    @NotBlank
    @NotNull
    private List<String> tags;

}
