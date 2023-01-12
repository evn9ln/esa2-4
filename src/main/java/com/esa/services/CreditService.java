package com.esa.services;

import com.esa.domain.Bank;
import com.esa.domain.Credit;
import com.esa.repo.CreditRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditService {
    public static List<String> getCreditNames(CreditRepository creditRepository, Bank bank) {
        List<String> creditList = new ArrayList<>();
        for (Credit credit : creditRepository.findAllByBankId(bank)) creditList.add(credit.getType());
        return creditList;
    }
}
