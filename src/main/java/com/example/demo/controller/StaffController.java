package com.example.demo.controller;

import com.example.demo.entities.Salaire;
import com.example.demo.entities.Staff;
import com.example.demo.services.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private ModelMapper modelMapper;

    // GET /api/staffs
    @GetMapping
    public ResponseEntity<Collection<Staff>> getAllStaffs() {
        return ResponseEntity.ok(staffService.getAll());
    }

    // GET /api/staffs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        Optional<Staff> staff = staffService.getById(id);
        return staff.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/staffs
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Staff savedStaff = staffService.save(staff);
        return ResponseEntity.ok(savedStaff);
    }

    // PUT /api/staffs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        Optional<Staff> existing = staffService.getById(id);
        if (existing.isPresent()) {
            staff.setId(id); // assure que l'ID est correctement défini
            return ResponseEntity.ok(staffService.update(staff));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/staffs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        Optional<Staff> existing = staffService.getById(id);
        if (existing.isPresent()) {
            staffService.deletById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            staffService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'upload de la photo");
        }
    }

    /**
     * Récupérer la photo
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Staff> staffOpt = staffService.getById(id);
        if (staffOpt.isPresent() && staffOpt.get().getPhoto() != null) {
            Staff staff = staffOpt.get();
            ByteArrayResource resource = new ByteArrayResource(staff.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + staff.getId() + "\"")
                    .contentType(MediaType.parseMediaType(staff.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
}
}
