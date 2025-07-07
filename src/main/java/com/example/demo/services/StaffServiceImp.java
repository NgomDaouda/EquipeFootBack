package com.example.demo.services;

import com.example.demo.entities.Staff;
import com.example.demo.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
public class StaffServiceImp implements StaffService{

    @Autowired
    private StaffRepository staffRepository;
    @Override
    public Collection<Staff> getAll() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> getById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff update(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void deletById(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public void uploadPhoto(Long staffId, MultipartFile file) throws IOException {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Salaire non trouvé avec l'ID: " + staffId));

        byte[] photoData = file.getBytes();
        String contentType = file.getContentType();

        staff.setPhoto(photoData);
        staff.setTypePhoto(contentType);
        staffRepository.save(staff);
    }
}
