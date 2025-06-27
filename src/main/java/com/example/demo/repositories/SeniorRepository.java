package com.example.demo.repositories;

import com.example.demo.entities.Senior;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorRepository extends JpaRepository<Senior,Long> {
}
