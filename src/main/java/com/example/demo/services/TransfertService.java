package com.example.demo.services;

import com.example.demo.dto.TransfertDto;
import com.example.demo.entities.Transfert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface TransfertService {

    Collection<Transfert> getAll();
    Optional<Transfert> getById(Long id);
    Transfert  save(Transfert transfert);
    Transfert  update(Transfert transfert);
    void delete(Long id);
    TransfertDto saveTransfert(TransfertDto dto);
    void uploadPhoto(Long seniorId, MultipartFile file) throws IOException;

}
