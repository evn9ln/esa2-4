package com.esa.services;

import com.esa.domain.CreditOffer;
import com.esa.domain.PaymentTimetable;
import com.esa.repo.PaymentTimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentTimetableService {

    @Autowired
    private PaymentTimetableRepository paymentTimetableRepository;

    public String getTimetable(UUID creditOfferId) {
        String timetable = "";
        List<PaymentTimetable> paymentTimetableList = paymentTimetableRepository.findAllByCreditOfferId(creditOfferId);

        if (!paymentTimetableList.isEmpty()) {

            for (PaymentTimetable paymentTimetable : paymentTimetableList.stream().sorted(Comparator.comparing(PaymentTimetable::getDate)).collect(Collectors.toList())) {
                timetable += "Payment date: " + paymentTimetable.getDate() + "\n"
                        + "Payment amount: " + paymentTimetable.getAmount() + "\n"
                        + "Credit body payment: " + paymentTimetable.getRepaymentAmount() + "\n"
                        + "Credit percents payment: " + paymentTimetable.getPercentRepaymentAmount() + "\n\n";
            }
        }
        return timetable;
    }


}
