package com.esa.repo;

import com.esa.domain.Bank;
import com.esa.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  Client findById(UUID uuid);

  Client findByName(String name);

  Client findByEmailAndPassport(String email, String passport);

  List<Client> findAllByBankId(Bank uuid);

  @Transactional
  void deleteById(UUID uuid);

}
