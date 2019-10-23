package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Client;

public interface ClientService extends ModelService<Client> {
    String TYPE_PERSON = "PERSON";
    String TYPE_COMPANY = "COMPANY";
}
