package org.erkamber.controllers;

import org.erkamber.dtos.RaceDTO;
import org.erkamber.requestDtos.RaceRequestDTO;
import org.erkamber.services.interfaces.RaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/races")
@CrossOrigin(origins = {"http://localhost:3000", "https://racing-app-amber.vercel.app"})
@Validated
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @PostMapping
    public ResponseEntity<RaceDTO> saveRace(@RequestBody RaceRequestDTO newRace) {
        // Call the save method of RaceService to save the new race
        RaceDTO savedRace = raceService.save(newRace);

        // Return the saved race in the response body with HTTP status 201 (Created)
        return new ResponseEntity<>(savedRace, HttpStatus.CREATED);
    }

    @GetMapping("/{raceId}")
    public RaceDTO getRaceById(@PathVariable long raceId) {
        return raceService.getById(raceId);
    }

    @GetMapping("/last/{racerId}")
    public ResponseEntity<RaceDTO> getLastRaceOfRacer(@PathVariable long racerId) {
        RaceDTO lastRace = raceService.getLastRaceOfRacer(racerId);
        return ResponseEntity.ok(lastRace);
    }

    @GetMapping
    public ResponseEntity<List<RaceDTO>> getAllRaces() {
        List<RaceDTO> raceDTOs = raceService.getAllRaces();
        return ResponseEntity.ok(raceDTOs);
    }

    @GetMapping("/racer/{racerId}")
    public List<RaceDTO> getRacesByRacerId(@PathVariable long racerId) {
        return raceService.getByRacerId(racerId);
    }

    @DeleteMapping("/racer/{racerId}")
    public List<RaceDTO> deleteRacerRaces(@PathVariable long racerId) {
        return raceService.deleteRacerRaces(racerId);
    }

    @DeleteMapping("/{raceId}")
    public RaceDTO deleteRaceById(@PathVariable long raceId) {
        return raceService.deleteById(raceId);
    }
}