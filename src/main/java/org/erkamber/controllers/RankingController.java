package org.erkamber.controllers;

import org.erkamber.dtos.RankingDTO;
import org.erkamber.services.interfaces.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ranking")
@CrossOrigin(origins = {"http://localhost:3000", "replace with remote id"})
@Validated
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping()
    public ResponseEntity<List<RankingDTO>> getAllBestLapTimes(
            @RequestParam("trackId") int trackId,
            @RequestParam("kartId") int kartId) {
        List<RankingDTO> bestLapDTOs = rankingService.getRankingByLapAndKart(trackId, kartId);
        return ResponseEntity.ok(bestLapDTOs);
    }

    @GetMapping("/best-last-session")
    public ResponseEntity<RankingDTO> getBestLastSession(
            @RequestParam("trackId") long trackId,
            @RequestParam("kartId") long kartId,
            @RequestParam("racerId") long racerId) {
        RankingDTO bestLastSession = rankingService.getBestLastSession(trackId, kartId, racerId);
        return ResponseEntity.ok(bestLastSession);
    }
}
