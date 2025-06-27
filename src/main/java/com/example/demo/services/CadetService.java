package com.example.demo.services;

import com.example.demo.dto.CadetDto;
import com.example.demo.dto.SeniorDto;
import com.example.demo.entities.Cadet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface CadetService {

    Collection<Cadet> getAll();
    Optional<Cadet> getById(Long id);
    Cadet save(Cadet cadet);
    Cadet update(Cadet cadet);
    void delete(Long id);
    CadetDto updateCadet(Long id, CadetDto cadetDto);

    void uploadPhoto(Long seniorId, MultipartFile file) throws IOException;

}
