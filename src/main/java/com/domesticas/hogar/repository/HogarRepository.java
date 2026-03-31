package com.domesticas.hogar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domesticas.hogar.model.Hogar;

public interface HogarRepository extends JpaRepository<Hogar, Long> {
}