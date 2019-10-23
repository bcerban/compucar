package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Workshop;

public interface WorkshopService extends ModelService<Workshop> {
    Workshop fetch(String code);
}
