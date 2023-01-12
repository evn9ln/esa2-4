package com.esa.services;

import com.esa.domain.Bank;
import com.esa.domain.Client;
import com.esa.repo.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    public static List<String> getClientNames(ClientRepository clientRepository) {
        List<String> clientNames = new ArrayList<String>();
        for (Client client : clientRepository.findAll())
            clientNames.add(client.getName());
        return clientNames;
    }

    public static void deleteBanks(ClientRepository clientRepository, Bank bankId) {
        List<Client> clients = clientRepository.findAllByBankId(bankId);
        for (Client client : clients) {
            client.setBank(null);
            clientRepository.save(client);
        }
    }

}
