package com.esa.view.entityTables;

import com.esa.domain.Credit;
import com.esa.extraClasses.NavigationBar;
import com.esa.repo.CreditOfferRepository;
import com.esa.repo.CreditRepository;
import com.esa.repo.UuidMapRepository;
import com.esa.services.CreditOfferService;
import com.esa.view.management.ManageCredit;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@Route("credits")
@PageTitle("Credits")
public class Credits extends VerticalLayout {
    private CreditRepository creditRepository;
    private CreditOfferRepository creditOfferRepository;
    private UuidMapRepository uuidMapRepository;

    private Grid<Credit> grid;
    private RouterLink manageLink;

    private final HorizontalLayout addCredits;

    @Autowired
    public Credits(CreditRepository creditRepository, CreditOfferRepository creditOfferRepository,
                   UuidMapRepository uuidMapRepository) {
        this.creditRepository = creditRepository;
        this.creditOfferRepository = creditOfferRepository;
        this.uuidMapRepository = uuidMapRepository;

        grid = new Grid<>();
        manageLink = new RouterLink("Add new credit", ManageCredit.class, 0);

        addCredits = new HorizontalLayout(manageLink);

        add(NavigationBar.getBar(), manageLink, grid);
    }

    @PostConstruct
    public void fillGrid() {
        List<Credit> creditList = creditRepository.findAll();

        if(!creditList.isEmpty()) {
            grid.addColumn(Credit::getId).setHeader("<UUID>").setSortable(true);
            grid.addColumn(Credit::getBankName).setHeader("Bank").setSortable(true);
            grid.addColumn(Credit::getType).setHeader("Type").setSortable(true);
            grid.addColumn(Credit::getLimit).setHeader("Limit").setSortable(true);
            grid.addColumn(Credit::getPercent).setHeader("Percent").setSortable(true);

            grid.addColumn(new NativeButtonRenderer<>("Change", credit -> {
                UI.getCurrent().navigate(ManageCredit.class, uuidMapRepository.findByUuid(credit.getId()).getId());
            }));

            grid.addColumn(new NativeButtonRenderer<>("Delete", credit -> {
                Dialog dialog = new Dialog();
                Button confirm = new Button("Delete");
                Button cancel = new Button("Cancel");
                Label label = new Label("Are you sure? It also deletes all credit offers of this credit ");

                dialog.add(label, confirm, cancel);

                confirm.addClickListener(e -> {
                    CreditOfferService.deleteAllByCredit(uuidMapRepository, creditOfferRepository, credit);
                    uuidMapRepository.deleteByUuid(credit.getId());

                    creditRepository.delete(credit);
                    dialog.close();

                    Notification notification = new Notification("Credit successfully deleted.", 1000);
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.open();

                    grid.setItems(creditRepository.findAll());
                });
                cancel.addClickListener(e -> {
                    dialog.close();
                });

                dialog.open();
            }));
            grid.setItems(creditList);
        }
    }
}
