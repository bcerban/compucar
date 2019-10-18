package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ClientRepository  // extends JpaRepository<Client, Long> { }
{
    List<Client> findAll();
}
