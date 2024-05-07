package org.erkamber.controllers;

import org.erkamber.dtos.RaceDTO;
import org.erkamber.requestDtos.RaceRequestDTO;
import org.erkamber.services.interfaces.RaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/races")
@CrossOrigin(origins = {"http://localhost:3000", "replace with remote id"})
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
}