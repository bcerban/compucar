package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.model.Service;

import java.util.Date;
import java.util.List;

public interface ServiceRepository extends Repository<Service> {
    List<Service> listByMechanicAndDate(Mechanic mechanic, Date date);
    List<Service> listByMonth(int month);
    List<Service> listByDateRange(Date from, Date to);
    List<Service> listByReaderAndDateRange(Reader reader, Date from, Date to);
    Service getByCode(String code);
    int getCountForClientOnDate(Client client, Date date);
    int getCountForClientOnMonth(Client client, Date to);
}
