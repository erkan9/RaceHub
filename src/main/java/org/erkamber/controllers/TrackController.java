package org.erkamber.controllers;

import org.erkamber.dtos.TrackDTO;
import org.erkamber.requestDtos.TrackRequestDTO;
import org.erkamber.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tracks")
@CrossOrigin(origins = {"http://localhost:3000", "replace with remote id"})
@Validated
public class TrackController {

    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/save")
    public ResponseEntity<TrackDTO> saveTrack(@RequestBody TrackRequestDTO newTrack) {
        TrackDTO savedTrack = trackService.save(newTrack);
        return new ResponseEntity<>(savedTrack, HttpStatus.CREATED);
    }

    @GetMapping("/{trackId}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable long trackId) {
        TrackDTO track = trackService.getById(trackId);
        return ResponseEntity.ok(track);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        List<TrackDTO> tracks = trackService.getAll();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/preferred-track/{racerId}")
    public ResponseEntity<TrackDTO> getMostPreferredTrack(@PathVariable long racerId) {
        TrackDTO preferredTrack = trackService.findMostPreferredTrackForRacer(racerId);
        return ResponseEntity.ok(preferredTrack);
    }

    @PatchMapping("/{trackId}")
    public ResponseEntity<TrackDTO> updateTrack(@RequestBody TrackRequestDTO trackToUpdate, @PathVariable long trackId) {
        TrackDTO updatedTrack = trackService.updateTrack(trackToUpdate, trackId);
        return ResponseEntity.ok(updatedTrack);
    }
}