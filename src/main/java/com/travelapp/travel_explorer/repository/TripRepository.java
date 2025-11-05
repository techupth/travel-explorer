package com.travelapp.travel_explorer.repository;

import com.travelapp.travel_explorer.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    
    List<Trip> findByAuthorId(Long authorId);
    
    List<Trip> findByTitleContainingIgnoreCase(String title);
    
    @Query(value = "SELECT * FROM trips WHERE :tag = ANY(tags)", nativeQuery = true)
    List<Trip> findByTagsContaining(@Param("tag") String tag);
    
    List<Trip> findAllByOrderByCreatedAtDesc();
}
