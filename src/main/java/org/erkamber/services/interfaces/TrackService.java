package org.erkamber.services.interfaces;

import org.erkamber.dtos.TrackDTO;
import org.erkamber.requestDtos.TrackRequestDTO;

import java.util.List;

public interface TrackService {

    TrackDTO save(TrackRequestDTO newTrack);

    TrackDTO getById(long trackId);

    List<TrackDTO> getAll();

    TrackDTO updateTrack(TrackRequestDTO trackToUpdate, long trackId);
}
