package com.esa.services;

import com.esa.aspects.Loggable;
import com.esa.domain.Client;
import com.esa.domain.Credit;
import com.esa.domain.CreditOffer;
import com.esa.repo.CreditOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreditOfferRepositoryService {

    private CreditOfferRepository creditOfferRepository;

    @Autowired
    public CreditOfferRepositoryService(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    @Loggable
    public void save(CreditOffer creditOffer) {
        creditOfferRepository.save(creditOffer);
    }

    @Loggable
    public void delete(CreditOffer creditOffer) {
        creditOfferRepository.delete(creditOffer);
    }

    public CreditOffer findById(UUID creditOfferId) {
        return creditOfferRepository.findById(creditOfferId);
    }

    public CreditOffer findByClientIdAndCreditId(Client client, Credit credit) {
        return creditOfferRepository.findByClientIdAndCreditId(client, credit);
    }
}
