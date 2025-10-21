package com.travelapp.travel_explorer.controller;

import com.travelapp.travel_explorer.dto.TripDto;
import com.travelapp.travel_explorer.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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
    
    @GetMapping("/eid/{eid}")
    public ResponseEntity<TripDto> getTripByEid(@PathVariable String eid) {
        return ResponseEntity.ok(tripService.getTripByEid(eid));
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
    public ResponseEntity<TripDto> createTrip(@Valid @RequestBody TripDto tripDto) {
        return ResponseEntity.ok(tripService.createTrip(tripDto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TripDto> updateTrip(
            @PathVariable Long id,
            @Valid @RequestBody TripDto tripDto) {
        return ResponseEntity.ok(tripService.updateTrip(id, tripDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
