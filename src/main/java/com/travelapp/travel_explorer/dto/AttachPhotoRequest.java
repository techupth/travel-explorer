package com.travelapp.travel_explorer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttachPhotoRequest {
    
    @NotBlank(message = "Photo URL is required")
    private String url;
}
