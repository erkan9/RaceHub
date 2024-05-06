package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.dtos.RacerDTO;
import org.erkamber.dtos.StatisticDTO;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Statistic;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.RacerRepository;
import org.erkamber.repositories.StatisticRepository;
import org.erkamber.requestDtos.RacerRequestDTO;
import org.erkamber.services.interfaces.RacerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RacerServiceImpl implements RacerService {

    private final RacerRepository racerRepository;
    private final ModelMapper mapper;

    private final StatisticRepository statisticRepository;

    public RacerServiceImpl(RacerRepository racerRepository, ModelMapper mapper, StatisticRepository statisticRepository) {
        this.racerRepository = racerRepository;
        this.mapper = mapper;
        this.statisticRepository = statisticRepository;
    }

    @Override
    public RacerDTO save(RacerRequestDTO newRacer) {

        Racer racer = mapper.map(newRacer, Racer.class);

        racer = racerRepository.save(racer);

        Statistic statistic = new Statistic();

        statistic.setRacer(racer);

        statisticRepository.save(statistic);

        return mapper.map(racer, RacerDTO.class);
    }


    @Override
    public RacerDTO getById(long racerId) {

        Racer racer = getRacerById(racerId);

        RacerDTO racerDTO = mapper.map(racer, RacerDTO.class);
        racerDTO.setRacerStatistics(mapper.map(racer.getStatistic(), StatisticDTO.class));

        return racerDTO;
    }

    @Override
    public RacerDTO getByEmailAndPassword(String email, String password) {

        Racer racer = getRacerByEmailAndPassword(email, password);

        return mapper.map(racer, RacerDTO.class);
    }

    @Override
    public RacerDTO deleteById(long racerId) {

        Racer racer = getRacerById(racerId);

        racerRepository.deleteById(racerId);

        return mapper.map(racer, RacerDTO.class);
    }

    @Override
    public RacerDTO update(RacerRequestDTO updateRacer, long racerId) {

        Racer racer = getRacerById(racerId);


        if (updateRacer.getFirstName() != null) {
            racer.setFirstName(updateRacer.getFirstName());
        }
        if (updateRacer.getLastName() != null) {
            racer.setLastName(updateRacer.getLastName());
        }
        if (updateRacer.getCity() != null) {
            racer.setCity(updateRacer.getCity());
        }
        if (updateRacer.getAgeRange() != null) {
            racer.setAgeRange(updateRacer.getAgeRange());
        }
        if (updateRacer.getEmail() != null) {
            racer.setEmail(updateRacer.getEmail());
        }
        if (updateRacer.getPassword() != null) {
            racer.setPassword(updateRacer.getPassword());
        }
        if (updateRacer.getPhoto() != null) {
            racer.setPhoto(updateRacer.getPhoto());
        }

        racer = racerRepository.save(racer);

        return mapper.map(racer, RacerDTO.class);
    }

    private Racer getRacerById(long racerId) {

        Optional<Racer> optionalRacer = racerRepository.findById(racerId);

        return optionalRacer.orElseThrow(() ->
                new ResourceNotFoundException("Racer not found with id: " + racerId, "Racer"));
    }

    private Racer getRacerByEmailAndPassword(String email, String password) {

        Optional<Racer> optionalRacer = racerRepository.findRacerByEmailAndPassword(email, password);

        return optionalRacer.orElseThrow(() ->
                new ResourceNotFoundException("Racer not found with email: " + email + " and password: " + password, "Racer"));
    }
}
