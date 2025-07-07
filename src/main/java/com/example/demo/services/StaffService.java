package com.example.demo.services;

import com.example.demo.entities.Staff;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface StaffService {
    Collection<Staff> getAll();
    Optional<Staff> getById(Long id);
    Staff save(Staff staff);
    Staff update(Staff staff);
    void deletById(Long id);

    void uploadPhoto(Long staffId, MultipartFile file) throws IOException;
}
