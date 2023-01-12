package com.esa.services;

import com.esa.domain.Bank;
import com.esa.domain.Client;
import com.esa.domain.Credit;
import com.esa.domain.CreditOffer;
import com.esa.repo.CreditOfferRepository;
import com.esa.repo.UuidMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditOfferService {

    public static void deleteAllByBank(UuidMapRepository uuidMapRepository, CreditOfferRepository creditOfferRepository, Bank bank) {
        List<CreditOffer> creditOfferList = creditOfferRepository.findAllByBankId(bank);
        if(!creditOfferList.isEmpty()) {
            for (CreditOffer creditOffer : creditOfferList) {
                uuidMapRepository.deleteByUuid(creditOffer.getId());
                creditOffer.removeCredit();
                creditOfferRepository.delete(creditOffer);
            }
        }
    }

    public static void deleteAllByClient(UuidMapRepository uuidMapRepository, CreditOfferRepository creditOfferRepository, Client client) {

        List<CreditOffer> creditOfferList = creditOfferRepository.findAllByClientId(client);
        if(!creditOfferList.isEmpty()) {
            for (CreditOffer creditOffer : creditOfferList) {
                uuidMapRepository.deleteByUuid(creditOffer.getId());
                creditOffer.removeCredit();
                creditOfferRepository.delete(creditOffer);
            }
        }
    }

    public static void deleteAllByCredit(UuidMapRepository uuidMapRepository, CreditOfferRepository creditOfferRepository, Credit credit) {

        List<CreditOffer> creditOfferList = creditOfferRepository.findAllByCreditId(credit);
        if(!creditOfferList.isEmpty()) {
            for (CreditOffer creditOffer : creditOfferList) {
                uuidMapRepository.deleteByUuid(creditOffer.getId());
                creditOffer.removeCredit();

                creditOfferRepository.delete(creditOffer);
            }
        }
    }
}
