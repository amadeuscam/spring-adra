package com.amadeuscam.adratorrejon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amadeuscam.adratorrejon.models.StockAlimentos;

public interface StockAlimentosRepository extends JpaRepository<StockAlimentos, Long> {
    // public Optional<StockAlimentos> findById(Long id);
}
