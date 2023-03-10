package com.esa.repo;

import com.esa.domain.Bank;
import com.esa.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
    List<Credit> findAllByBankId(Bank bank);

    Credit findById(UUID uuid);

    Credit findByType(String type);

    Credit findByTypeAndAndBankId(String type, Bank bank);

    @Transactional
    void deleteAllByBankId(Bank bank);
}
