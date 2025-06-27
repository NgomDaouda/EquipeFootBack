package com.example.demo.repositories;

import com.example.demo.entities.Salaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaireRepository extends JpaRepository<Salaire, Long> {
}
