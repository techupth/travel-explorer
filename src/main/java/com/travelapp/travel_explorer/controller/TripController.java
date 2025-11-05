package com.travelapp.travel_explorer.controller;

import com.travelapp.travel_explorer.dto.AttachPhotoRequest;
import com.travelapp.travel_explorer.dto.TripDto;
import com.travelapp.travel_explorer.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    
    private final TripService tripService;
    
    @GetMapping
    public ResponseEntity<List<TripDto>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getTripById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }
    
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<TripDto>> getTripsByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(tripService.getTripsByAuthor(authorId));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<TripDto>> searchTrips(@RequestParam String query) {
        return ResponseEntity.ok(tripService.searchTrips(query));
    }
    
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<TripDto>> getTripsByTag(@PathVariable String tag) {
        return ResponseEntity.ok(tripService.getTripsByTag(tag));
    }
    
    @PostMapping
    public ResponseEntity<TripDto> createTrip(
            @Valid @RequestBody TripDto tripDto,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(tripService.createTrip(tripDto, username));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TripDto> updateTrip(
            @PathVariable Long id,
            @Valid @RequestBody TripDto tripDto,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(tripService.updateTrip(id, tripDto, username));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(
            @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        tripService.deleteTrip(id, username);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/photos")
    public ResponseEntity<TripDto> attachPhotoUrl(
            @PathVariable Long id,
            @Valid @RequestBody AttachPhotoRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(tripService.attachPhotoUrl(id, request.getUrl(), username));
    }
}
