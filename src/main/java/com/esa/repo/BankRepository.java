package com.esa.repo;

import com.esa.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    Bank findByName(String name);

    Bank findById(UUID uuid);
}
