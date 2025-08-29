package com.example.Box_management.repository;

import com.example.Box_management.entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box, Long> {
}
