package org.erkamber.controllers;

import org.erkamber.dtos.RacerDTO;
import org.erkamber.requestDtos.RacerRequestDTO;
import org.erkamber.services.interfaces.RacerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/racers")
@CrossOrigin(origins = {"http://localhost:3000", "racing-app-amber.vercel.app"})
@Validated
public class RacerController {

    private final RacerService racerService;

    public RacerController(RacerService racerService) {
        this.racerService = racerService;
    }

    @PostMapping("/save")
    public ResponseEntity<RacerDTO> saveRacer(@RequestBody RacerRequestDTO newRacer) {
        RacerDTO savedRacer = racerService.save(newRacer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Racer-Id", String.valueOf(savedRacer.getRacerId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedRacer);
    }

    @GetMapping("/{racerId}")
    public ResponseEntity<RacerDTO> getRacerById(@PathVariable long racerId) {
        RacerDTO racer = racerService.getById(racerId);
        return ResponseEntity.ok(racer);
    }

    @GetMapping("/login")
    public ResponseEntity<RacerDTO> getRacerByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        RacerDTO racer = racerService.getByEmailAndPassword(email, password);
        return ResponseEntity.ok(racer);
    }

    @GetMapping("/by-email")
    public ResponseEntity<RacerDTO> getRacerByEmail(@RequestParam String email) {
        RacerDTO racerDTO = racerService.getByEmail(email);
        return ResponseEntity.ok(racerDTO);
    }

    @DeleteMapping("/{racerId}")
    public ResponseEntity<RacerDTO> deleteRacerById(@PathVariable long racerId) {
        RacerDTO deletedRacer = racerService.deleteById(racerId);
        return ResponseEntity.ok(deletedRacer);
    }

    @PatchMapping("/{racerId}")
    public ResponseEntity<RacerDTO> updateRacer(@RequestBody RacerRequestDTO updateRacer, @PathVariable long racerId) {
        RacerDTO updatedRacer = racerService.update(updateRacer, racerId);
        return ResponseEntity.ok(updatedRacer);
    }
}