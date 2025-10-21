package com.travelapp.travel_explorer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {
    
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Photos are required")
    private String[] photos;
    
    @NotNull(message = "Tags are required")
    private String[] tags;
    
    private Double latitude;
    
    private Double longitude;
    
    private Long authorId;
    
    private String authorDisplayName;
    
    private OffsetDateTime createdAt;
    
    private OffsetDateTime updatedAt;
}
