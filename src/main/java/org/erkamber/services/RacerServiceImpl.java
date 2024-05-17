package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.configurations.PasswordEncoderConfiguration;
import org.erkamber.dtos.RacerDTO;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Statistic;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.RacerRepository;
import org.erkamber.repositories.StatisticRepository;
import org.erkamber.requestDtos.RacerRequestDTO;
import org.erkamber.services.interfaces.RacerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RacerServiceImpl implements RacerService {

    private final RacerRepository racerRepository;

    private final ModelMapper mapper;

    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    private final StatisticRepository statisticRepository;

    public RacerServiceImpl(RacerRepository racerRepository, ModelMapper mapper,
                            PasswordEncoderConfiguration passwordEncoderConfiguration, StatisticRepository statisticRepository) {
        this.racerRepository = racerRepository;
        this.mapper = mapper;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
        this.statisticRepository = statisticRepository;
    }

    @Override
    public RacerDTO save(RacerRequestDTO newRacer) {

        Racer racer = mapper.map(newRacer, Racer.class);

        String encodedRacerPassword = passwordEncoderConfiguration.passwordEncoder().encode(racer.getPassword());

        racer.setPassword(encodedRacerPassword);

        racer = racerRepository.save(racer);

        Statistic statistic = new Statistic();

        statistic.setRacer(racer);

        statisticRepository.save(statistic);

        return mapper.map(racer, RacerDTO.class);
    }


    @Override
    public RacerDTO getById(long racerId) {

        Racer racer = getRacerById(racerId);

        return mapper.map(racer, RacerDTO.class);
    }

    @Override
    public RacerDTO getByEmail(String racerEmail) {

        Optional<Racer> optionalRacer = racerRepository.findRacerByEmail(racerEmail);

        Racer racer = optionalRacer.orElseThrow(() ->
                new ResourceNotFoundException("Racer not found with email: " + racerEmail, "Racer"));

        return mapper.map(racer, RacerDTO.class);
    }

    @Override
    public RacerDTO getByEmailAndPassword(String email, String password) {

        Racer racer = getLoginRacer(email, password);

        return mapper.map(racer, RacerDTO.class);
    }

    private Racer getLoginRacer(String racerEmail, String password) {

        List<Racer> allRacers = racerRepository.findAll();

        return allRacers.stream()
                .filter(racer -> racer.getEmail().equals(racerEmail) &&
                        passwordEncoderConfiguration.passwordEncoder().matches(password, racer.getPassword()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Incorrect email or password!", "Racer"));
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

    Racer getRacerById(long racerId) {

        Optional<Racer> optionalRacer = racerRepository.findById(racerId);

        return optionalRacer.orElseThrow(() ->
                new ResourceNotFoundException("Racer not found with id: " + racerId, "Racer"));
    }

}
