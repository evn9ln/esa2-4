package com.esa.view.subViews;

import com.esa.domain.CreditOffer;
import com.esa.repo.CreditOfferRepository;
import com.esa.repo.PaymentTimetableRepository;
import com.esa.repo.UuidMapRepository;
import com.esa.services.PaymentTimetableService;
import com.esa.view.entityTables.CreditOffers;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Route("creditInfo")
@PageTitle("Credit info")
public class CreditInfo extends VerticalLayout implements HasUrlParameter<Integer> {
    private UUID id;
    private TextField name;
    private TextField bank;
    private TextField creditType;
    private TextField months;
    private TextField amount;
    private TextField totalSumToPay;
    private TextField totalSumPercents;
    private TextField minMonthlyPay;
    private TextField balance;
    private Label titleTimetable;
    private TextArea timetable;

    private Button returnBtn;

    private CreditOfferRepository creditOfferRepository;
    private PaymentTimetableRepository paymentTimetableRepository;
    private UuidMapRepository uuidMapRepository;

    private HorizontalLayout title;

    private PaymentTimetableService paymentTimetableService;

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer intByUuid) {
        if(uuidMapRepository.findById(intByUuid).isPresent())
            id = uuidMapRepository.findById(intByUuid).get().getUuid();
        fillPage();
    }

    @Autowired
    public CreditInfo(CreditOfferRepository creditOfferRepository,
                      UuidMapRepository uuidMapRepository,
                      PaymentTimetableRepository paymentTimetableRepository,
                      PaymentTimetableService paymentTimetableService) {
        this.creditOfferRepository = creditOfferRepository;
        this.uuidMapRepository = uuidMapRepository;
        this.paymentTimetableRepository = paymentTimetableRepository;
        this.paymentTimetableService = paymentTimetableService;

        title = new HorizontalLayout();
        title.add(new H3("Credit offer information"));

        returnBtn = new Button("return");

        name = new TextField("Client name");
        bank = new TextField("Bank");
        creditType = new TextField("Credit type");
        amount = new TextField("Amount");
        months = new TextField("Months");
        totalSumToPay = new TextField("Total sum to pay");
        totalSumPercents = new TextField("Total sum percents(overpay)");
        minMonthlyPay = new TextField("Minimal monthly pay");
        balance = new TextField("Current balance");
        titleTimetable = new Label("Payment timetable");
        timetable = new TextArea();

        add(title, name, bank, creditType, amount, months, totalSumToPay,
                totalSumPercents, minMonthlyPay, balance, titleTimetable, timetable, returnBtn);
    }

    public void fillPage() {
        CreditOffer creditOffer = creditOfferRepository.findById(id);

        if(creditOffer != null) {
            name.setValue(creditOffer.getClientName());
            name.setReadOnly(true);
            bank.setValue(creditOffer.getBankName());
            bank.setReadOnly(true);
            creditType.setValue(creditOffer.getCreditType());
            creditType.setReadOnly(true);
            amount.setValue(Integer.toString(creditOffer.getAmount()));
            amount.setReadOnly(true);
            months.setValue(Integer.toString(creditOffer.getMonths()));
            months.setReadOnly(true);
            int total = (int) (creditOffer.getAmount()
                    * (1 + (double) (creditOffer.getCreditId().getPercent()) / 100));
            totalSumToPay.setValue(Integer.toString(total));
            totalSumToPay.setReadOnly(true);
            totalSumPercents.setValue(Integer.toString(total - creditOffer.getAmount()));
            totalSumPercents.setReadOnly(true);
            minMonthlyPay.setValue(Integer.toString(total / creditOffer.getMonths()));
            minMonthlyPay.setReadOnly(true);

            if (creditOffer.getBalance() != 0) {
                balance.setValue(Integer.toString(creditOffer.getBalance()));

                if (creditOffer.getBalance() == total) {
                    timetable.setValue(paymentTimetableService.getTimetable(creditOffer.getId()) + "\n\t\tCREDIT CLOSED");
                } else {
                    timetable.setValue(paymentTimetableService.getTimetable(creditOffer.getId()));
                }

                timetable.setWidth("322px");
            } else {
                balance.setValue(Integer.toString(0));
                titleTimetable.setVisible(false);
                timetable.setVisible(false);
            }
            timetable.setReadOnly(true);
            balance.setReadOnly(true);
        }
        else {
            name.setVisible(false);
            bank.setVisible(false);
            creditType.setVisible(false);
            amount.setVisible(false);
            months.setVisible(false);
            totalSumToPay.setVisible(false);
            totalSumPercents.setVisible(false);
            minMonthlyPay.setVisible(false);
            balance.setVisible(false);
            titleTimetable.setVisible(false);
            timetable.setVisible(false);


        }
            returnBtn.addClickListener(e -> {
                UI.getCurrent().navigate(CreditOffers.class);
            });

    }
}
