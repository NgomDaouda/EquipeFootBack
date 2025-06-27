package com.example.demo.repositories;

import com.example.demo.entities.Cadet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CadetRepository extends JpaRepository<Cadet,Long> {
}
