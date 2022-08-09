package com.devsuperior.bds02.servicies;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.servicies.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO dto){
       try {
        Event entity = repository.getOne(id);
        copyDtoToEntity(dto, entity);
        repository.save(entity);
        return  new EventDTO(entity);
       } catch (EntityNotFoundException e) {
           throw new ResourceNotFoundException("Resource not found");
       }
    }

    private void copyDtoToEntity(EventDTO dto, Event entity) {

        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());
        entity.setCity(new City(dto.getId(), null));
        Optional<City> city = cityRepository.findById(dto.getCityId());
        City c = city.orElseThrow(() -> new ResourceNotFoundException("city id does not exist"));
        entity.setCity(c);
    }
}
