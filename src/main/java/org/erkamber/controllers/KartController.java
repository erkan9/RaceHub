package org.erkamber.controllers;

import org.erkamber.dtos.KartDTO;
import org.erkamber.requestDtos.KartRequestDTO;
import org.erkamber.services.interfaces.KartService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/karts")
@CrossOrigin(origins = {"http://localhost:3000", "replace with remote id"})
@Validated
public class KartController {

    private final KartService kartService;

    public KartController(KartService kartService) {
        this.kartService = kartService;
    }

    @PostMapping("/save")
    public ResponseEntity<KartDTO> saveKart(@Valid @RequestBody KartRequestDTO newKart) {
        KartDTO savedKart = kartService.save(newKart);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/karts/" + savedKart.getKartId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedKart);
    }

    @GetMapping("/{kartId}")
    public ResponseEntity<KartDTO> getKartById(@PathVariable long kartId) {
        KartDTO kart = kartService.getById(kartId);
        return ResponseEntity.ok(kart);
    }

    @GetMapping("/all")
    public ResponseEntity<List<KartDTO>> getAllKarts() {
        List<KartDTO> karts = kartService.getAll();
        return ResponseEntity.ok(karts);
    }

    @PatchMapping("/{kartId}")
    public ResponseEntity<KartDTO> updateKart(@RequestBody KartRequestDTO updateKart, @PathVariable long kartId) {
        KartDTO updatedKart = kartService.update(updateKart, kartId);
        return ResponseEntity.ok(updatedKart);
    }
}
