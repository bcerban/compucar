package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> { }
