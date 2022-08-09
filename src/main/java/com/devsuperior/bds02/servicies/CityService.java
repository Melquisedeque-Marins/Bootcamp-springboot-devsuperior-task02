package com.devsuperior.bds02.servicies;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.servicies.exceptions.DatabaseException;
import com.devsuperior.bds02.servicies.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<CityDTO> findAll(){
        List<City> cities = repository.findAll(Sort.by("name"));
        List<CityDTO> listDTO = cities.stream().map(c -> new CityDTO(c)).collect(Collectors.toList());
        return listDTO;
    }

    public CityDTO insert(CityDTO dto){
        City entity = new City(null, dto.getName());
        entity = repository.save(entity);
        return new CityDTO(entity);
    }

    public void deleteById(Long id){
        try{
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("id not found");
        }catch (DataIntegrityViolationException e){
            throw  new DatabaseException("this city has events linked");
        }
    }
}
