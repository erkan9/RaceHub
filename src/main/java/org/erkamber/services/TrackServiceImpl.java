package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.dtos.TrackDTO;
import org.erkamber.entities.Race;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Track;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.RaceRepository;
import org.erkamber.repositories.RacerRepository;
import org.erkamber.repositories.TrackRepository;
import org.erkamber.requestDtos.TrackRequestDTO;
import org.erkamber.services.interfaces.TrackService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    private final RacerRepository racerRepository;

    private final RaceRepository raceRepository;

    private final ModelMapper mapper;

    public TrackServiceImpl(TrackRepository trackRepository, RacerRepository racerRepository, RaceRepository raceRepository, ModelMapper mapper) {
        this.trackRepository = trackRepository;
        this.racerRepository = racerRepository;
        this.raceRepository = raceRepository;
        this.mapper = mapper;
    }

    @Override
    public TrackDTO save(TrackRequestDTO newTrack) {

        Track track = mapper.map(newTrack, Track.class);

        Track savedTrack = trackRepository.save(track);

        return mapper.map(savedTrack, TrackDTO.class);
    }

    @Override
    public TrackDTO getById(long trackId) {

        Track track = getTrackById(trackId);

        return mapper.map(track, TrackDTO.class);
    }


    @Override
    public TrackDTO findMostPreferredTrackForRacer(long racerId) {
        // Retrieve the racer by ID
        Optional<Racer> racerOptional = racerRepository.findById(racerId);
        if (!racerOptional.isPresent()) {
            // Handle the case where the racer is not found
            throw new NoSuchElementException("Racer not found with ID: " + racerId);
        }

        Racer racer = racerOptional.get();

        // Retrieve all races for the given racer
        List<Race> allRaces = raceRepository.findRaceByRacer(racer);

        if (allRaces.isEmpty()) {
            // Handle the case where the racer has no races
            return null; // Or throw an exception, or return a default value
        }

        // Count the frequency of each track ID
        Map<Long, Long> trackFrequency = allRaces.stream()
                .collect(Collectors.groupingBy(race -> race.getTrack().getTrackId(), Collectors.counting()));

        // Find the track ID with the highest frequency
        long mostPreferredTrackId = trackFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        // Retrieve the most preferred track
        Track mostPreferredTrack = trackRepository.findById(mostPreferredTrackId)
                .orElseThrow(() -> new NoSuchElementException("Track not found with ID: " + mostPreferredTrackId));

        // Convert the Track entity to a TrackDTO
       return mapper.map(mostPreferredTrack, TrackDTO.class);
    }

    @Override
    public List<TrackDTO> getAll() {

        List<Track> tracks = trackRepository.findAll();

        return tracks.stream()
                .map(track -> mapper.map(track, TrackDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TrackDTO updateTrack(TrackRequestDTO trackToUpdate, long trackId) {

        Track track = getTrackById(trackId);

        if (trackToUpdate.getTrackName() != null) {
            track.setTrackName(trackToUpdate.getTrackName());
        }
        if (trackToUpdate.getCity() != null) {
            track.setCity(trackToUpdate.getCity());
        }
        if (trackToUpdate.getTrackLengthKms() > 0) {
            track.setTrackLengthKms(trackToUpdate.getTrackLengthKms());
        }
        if (trackToUpdate.getBestTrackTime() != null) {
            Duration bestTrackTime = trackToUpdate.getBestTrackTime();
            track.setBestTrackTime(bestTrackTime);
        }

        track = trackRepository.save(track);

        return mapper.map(track, TrackDTO.class);
    }

    @Override
    public List<TrackDTO> getByCity(String city) {

        List<Track> tracksByCity = trackRepository.findTrackByCity(city);

        return tracksByCity.stream()
                .map(track -> mapper.map(track, TrackDTO.class))
                .collect(Collectors.toList());
    }

    private Track getTrackById(long trackId) {

        Optional<Track> optionalTrack = trackRepository.findById(trackId);

        Track track = optionalTrack.orElseThrow(() -> {
            String errorMessage = "Track not found with id: " + trackId;
            log.error("Track not found with id: " + trackId);
            return new ResourceNotFoundException(errorMessage, "Track");
        });

        log.info("Track found with id: {}", trackId);
        return track;
    }
}
