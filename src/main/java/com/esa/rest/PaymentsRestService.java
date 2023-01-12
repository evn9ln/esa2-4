package com.esa.rest;

import com.esa.domain.PaymentTimetable;
import com.esa.repo.PaymentTimetableRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("restapi")
public class PaymentsRestService {

    private final PaymentTimetableRepository paymentTimetableRepository;

    @Autowired
    public PaymentsRestService(PaymentTimetableRepository paymentTimetableRepository) {
        this.paymentTimetableRepository = paymentTimetableRepository;
    }


    @GetMapping("/payments/json/{id}")
    public ResponseEntity<List<PaymentTimetable>> getJsonPayments(@PathVariable(name = "id") String creditOfferId) {
        UUID formattedId = UUID.fromString(creditOfferId);

        List<PaymentTimetable> payments = paymentTimetableRepository.findAllByCreditOfferId(formattedId);

        return payments != null
                ? new ResponseEntity<>(payments, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/payments/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ModelAndView  getXMLPayments(@PathVariable(name = "id") String creditOfferId) throws JsonProcessingException {
        UUID formattedId = UUID.fromString(creditOfferId);

        List<PaymentTimetable> payments = paymentTimetableRepository.findAllByCreditOfferId(formattedId);

        ModelAndView modelAndView = new ModelAndView("payments");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(payments)));
        modelAndView.addObject(source);

        return modelAndView;
    }
}
