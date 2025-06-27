package com.example.demo.services;

import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.SeniorDto;
import com.example.demo.entities.Senior;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface SeniorService {
    Collection<Senior> getAll();
    Optional<Senior> getById(Long id);
    Senior save(Senior senior);
    Senior update(Senior senior);
    void delete(Long id);
    SeniorDto updateSenior(Long id, SeniorDto seniorDto);
    void uploadPhoto(Long seniorId, MultipartFile file) throws IOException;

}
