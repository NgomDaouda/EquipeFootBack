package com.example.demo.repositories;

import com.example.demo.entities.Pret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PretRepository extends JpaRepository<Pret, Long> {
}
