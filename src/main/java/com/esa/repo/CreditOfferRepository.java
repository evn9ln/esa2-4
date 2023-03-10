package com.esa.repo;

import com.esa.domain.Bank;
import com.esa.domain.Client;
import com.esa.domain.Credit;
import com.esa.domain.CreditOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CreditOfferRepository extends JpaRepository<CreditOffer, Integer> {
    CreditOffer findByClientId(UUID uuid);

    CreditOffer findById(UUID uuid);

    CreditOffer findByClientIdAndCreditId(Client client, Credit credit);

    List<CreditOffer> findAllByBankId(Bank bank);

    List<CreditOffer> findAllByClientId(Client client);

    List<CreditOffer> findAllByCreditId(Credit credit);

}
