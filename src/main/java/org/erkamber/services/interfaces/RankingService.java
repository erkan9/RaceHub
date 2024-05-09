package org.erkamber.services.interfaces;

import org.erkamber.dtos.RankingDTO;

import java.util.List;

public interface RankingService {

    List<RankingDTO> getRankingByLapAndKart(long trackId, long kartId);

    RankingDTO getBestLastSession(long trackId, long kartId, long racerId);
}
