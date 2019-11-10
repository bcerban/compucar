package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.model.*;
import com.cerbansouto.compucar.services.dataAccess.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private ServiceRepository repository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private EventService eventService;

    @Value("${minimum.battery.life.required}")
    private int minimumBatteryLifeRequired;

    @Value("${maximum.daily.service.for.client}")
    private int maximumDailyServicesForClient;

    @Value("${maximum.daily.service.for.mechanic}")
    private int maximumDailyServicesForMechanic;

    @Value("${minimum.monthly.service.for.discount}")
    private int minimumMonthlyServicesForDiscount;

    @Transactional
    @Override
    public Service create(Service service) throws InvalidEntityException {
        log.info(String.format("###### Creating service with code %s ######", service.getCode()));
        validateService(service);

        try {
            Service created = repository.create(service);
            service.getReader().setBatteryUsed(service.getReader().getBatteryUsed() + service.getServiceTime());
            readerService.update(service.getReader());

            // TODO: move this to a new thread
            created.getEvents().forEach(e -> {
                e.setServiceCode(created.getCode());
                eventService.create(e);
            });

            return created;
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create service. Please check all information is correct.", e);
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException(e.getMessage(), e);
        }
    }

    @Override
    public List<Service> getForMonth(long month) {
        return this.repository.listByMonth(month);
    }

    private void validateService(Service service) throws InvalidEntityException {
        validateServiceCode(service.getCode());
        validateClient(service.getClient());
        validateMechanic(service.getMechanic());
        validateWorkshop(service.getWorkshop());
        validateReader(service.getReader());

        service.setClient(clientService.fetch(service.getClient().getNumber()));
        if (service.getClient().getType().equals(ClientService.TYPE_PERSON)) {
            int serviceCount = repository.getCountForClientOnDate(service.getClient(), service.getDate());
            if (serviceCount >= maximumDailyServicesForClient) {
                throw new InvalidEntityException("This client can't submit any more orders today.");
            }
        } else {
            int serviceCount = repository.getCountForClientOnMonth(service.getClient(), service.getDate());
            if (serviceCount >= minimumMonthlyServicesForDiscount) {
                service.setCost((float)(service.getCost() * 0.8));
            }
        }

        service.setMechanic(mechanicService.fetch(service.getMechanic().getNumber()));
        List<Service> servicesForMechanic = repository.listByMechanicAndDate(service.getMechanic(), service.getDate());
        if (servicesForMechanic.size() > maximumDailyServicesForMechanic) {
            throw new InvalidEntityException("This mechanic can't submit any more orders today.");
        }

        for (Service s : servicesForMechanic) {
            if (s.getWorkshop().getCode() != service.getWorkshop().getCode()) {
                throw new InvalidEntityException("Mechanic is already assigned to a different workshop.");
            }
        }

        service.setReader(readerService.fetch(service.getReader().getCode()));
        if (service.getReader().getRemainingBatteryLife() < minimumBatteryLifeRequired) {
            throw new InvalidEntityException("Reader is not available at the moment.");
        }

        if (!service.getReader().getWorkshop().getCode().equals(service.getWorkshop().getCode())) {
            throw new InvalidEntityException("Reader is not assigned to the requested workshop.");
        }

        service.setWorkshop(service.getReader().getWorkshop());
    }

    private void validateServiceCode(String code) throws InvalidEntityException {
        if (code == null || code.isEmpty()) {
            throw new InvalidEntityException("Service code cannot be empty.");
        }
    }

    private void validateClient(Client client) throws InvalidEntityException {
        if (client == null || client.getNumber() == null || client.getNumber() == 0) {
            throw new InvalidEntityException("Client code cannot be empty.");
        }
    }

    private void validateMechanic(Mechanic mechanic) throws InvalidEntityException {
        if (mechanic == null || mechanic.getNumber() == 0) {
            throw new InvalidEntityException("Mechanic code cannot be empty.");
        }
    }

    private void validateWorkshop(Workshop workshop) throws InvalidEntityException {
        if (workshop == null || workshop.getCode() == null || workshop.getCode().isEmpty()) {
            throw new InvalidEntityException("Workshop code cannot be empty.");
        }
    }

    private void validateReader(Reader reader) throws InvalidEntityException {
        if (reader == null || reader.getCode() == null || reader.getCode().isEmpty()) {
            throw new InvalidEntityException("Reader code cannot be empty.");
        }
    }
}
