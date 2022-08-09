package com.devsuperior.bds02.servicies;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

}
