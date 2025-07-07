package com.example.demo.repositories;

import com.example.demo.entities.Comptabilite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComptabiliteRepository extends JpaRepository<Comptabilite, Long> {
}
