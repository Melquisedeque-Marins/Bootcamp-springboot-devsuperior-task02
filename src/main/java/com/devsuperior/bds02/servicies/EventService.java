package com.devsuperior.bds02.servicies;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.servicies.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public EventDTO update(Long id, EventDTO dto){
       try {
        Event entity = repository.getOne(id);
        BeanUtils.copyProperties(dto, entity);
        repository.save(entity);
        return  new EventDTO(entity);
       } catch (EntityNotFoundException e) {
           throw new ResourceNotFoundException("Resource not found");
       }
    }
}
