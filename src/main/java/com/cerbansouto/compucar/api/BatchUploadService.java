package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.*;

import java.util.List;

public interface BatchUploadService {
    void create(List<Client> clients,
                List<Mechanic> mechanics,
                List<Workshop> workshops,
                List<Reader> readers,
                List<Service> services);
}
