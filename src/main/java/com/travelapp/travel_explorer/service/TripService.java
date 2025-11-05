package com.travelapp.travel_explorer.service;

import com.travelapp.travel_explorer.dto.TripDto;
import com.travelapp.travel_explorer.entity.Trip;
import com.travelapp.travel_explorer.entity.User;
import com.travelapp.travel_explorer.repository.TripRepository;
import com.travelapp.travel_explorer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {
    
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    
    public List<TripDto> getAllTrips() {
        return tripRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public TripDto getTripById(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
        return convertToDto(trip);
    }
    
    public List<TripDto> getTripsByAuthor(Long authorId) {
        return tripRepository.findByAuthorId(authorId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TripDto> searchTrips(String query) {
        return tripRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TripDto> getTripsByTag(String tag) {
        return tripRepository.findByTagsContaining(tag).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public TripDto createTrip(TripDto tripDto, String username) {
        // ✅ หา user จาก username (email)
        User author = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        Trip trip = convertToEntity(tripDto);
        trip.setAuthor(author); // ✅ ตั้ง author เป็นคนที่ login
        
        Trip saved = tripRepository.save(trip);
        return convertToDto(saved);
    }
    
    @Transactional
    public TripDto updateTrip(Long id, TripDto tripDto, String username) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
        
        // ✅ ตรวจสอบว่าเป็นเจ้าของหรือไม่
        if (!trip.getAuthor().getEmail().equals(username)) {
            throw new RuntimeException("Unauthorized: You can only edit your own trips");
        }
        
        trip.setTitle(tripDto.getTitle());
        trip.setDescription(tripDto.getDescription());
        trip.setPhotos(tripDto.getPhotos());
        trip.setTags(tripDto.getTags());
        trip.setLatitude(tripDto.getLatitude());
        trip.setLongitude(tripDto.getLongitude());
        
        Trip updated = tripRepository.save(trip);
        return convertToDto(updated);
    }
    
    @Transactional
    public void deleteTrip(Long id, String username) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
        
        // ✅ ตรวจสอบว่าเป็นเจ้าของหรือไม่
        if (!trip.getAuthor().getEmail().equals(username)) {
            throw new RuntimeException("Unauthorized: You can only delete your own trips");
        }
        
        tripRepository.deleteById(id);
    }
    
    @Transactional
    public TripDto attachPhotoUrl(Long id, String url, String username) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));
        
        // ✅ ตรวจสอบว่าเป็นเจ้าของหรือไม่
        if (!trip.getAuthor().getEmail().equals(username)) {
            throw new RuntimeException("Unauthorized: You can only edit your own trips");
        }
        
        // เพิ่ม URL ใหม่เข้าไปใน photos array
        String[] currentPhotos = trip.getPhotos();
        String[] newPhotos = new String[currentPhotos.length + 1];
        System.arraycopy(currentPhotos, 0, newPhotos, 0, currentPhotos.length);
        newPhotos[currentPhotos.length] = url;
        
        trip.setPhotos(newPhotos);
        Trip saved = tripRepository.save(trip);
        return convertToDto(saved);
    }
    
    private TripDto convertToDto(Trip trip) {
        TripDto dto = new TripDto();
        dto.setId(trip.getId());
        dto.setTitle(trip.getTitle());
        dto.setDescription(trip.getDescription());
        dto.setPhotos(trip.getPhotos());
        dto.setTags(trip.getTags());
        dto.setLatitude(trip.getLatitude());
        dto.setLongitude(trip.getLongitude());
        
        if (trip.getAuthor() != null) {
            dto.setAuthorId(trip.getAuthor().getId());
            dto.setAuthorDisplayName(trip.getAuthor().getDisplayName());
        }
        
        dto.setCreatedAt(trip.getCreatedAt());
        dto.setUpdatedAt(trip.getUpdatedAt());
        
        return dto;
    }
    
    private Trip convertToEntity(TripDto dto) {
        Trip trip = new Trip();
        trip.setTitle(dto.getTitle());
        trip.setDescription(dto.getDescription());
        trip.setPhotos(dto.getPhotos() != null ? dto.getPhotos() : new String[0]);
        trip.setTags(dto.getTags() != null ? dto.getTags() : new String[0]);
        trip.setLatitude(dto.getLatitude());
        trip.setLongitude(dto.getLongitude());
        
        if (dto.getAuthorId() != null) {
            User author = userRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            trip.setAuthor(author);
        }
        
        return trip;
    }
}

