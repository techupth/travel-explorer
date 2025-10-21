package com.travelapp.travel_explorer.repository;

import com.travelapp.travel_explorer.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    
    Optional<Trip> findByEid(String eid);
    
    List<Trip> findByAuthorId(Long authorId);
    
    List<Trip> findByTitleContainingIgnoreCase(String title);
    
    List<Trip> findByTagsContaining(String tag);
    
    List<Trip> findAllByOrderByCreatedAtDesc();
}
