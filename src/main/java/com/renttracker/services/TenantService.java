package com.renttracker.services;

import com.renttracker.domain.PaidTenant;
import com.renttracker.domain.Tenant;
import com.renttracker.repositories.HistoryRepository;
import com.renttracker.repositories.PhotoRepository;
import com.renttracker.repositories.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final HistoryRepository historyRepository;
    private final PhotoRepository photoRepository;

    public TenantService(TenantRepository tenantRepository, HistoryRepository historyRepository, PhotoRepository photoRepository) {
        this.tenantRepository = tenantRepository;
        this.historyRepository = historyRepository;
        this.photoRepository = photoRepository;
    }

    public void addTenant(Tenant tenant) {
        if (tenant.getDueDate().getYear() < LocalDate.now().getYear() - 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please type a more present due date");
        }

        tenant.setName(tenant.getName().strip());
        tenantRepository.save(tenant);
    }

    public List<Tenant> getTenants() {
            return tenantRepository.findAll();
    }

    public List<PaidTenant> getHistory() {
        return historyRepository.findAll();
    }

    public void removeTenant(Integer id) {
        if (!(tenantRepository.existsById(id))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found!");
        }
        tenantRepository.deleteById(id);
        photoRepository.findByTenantId(id).ifPresent(photoRepository::delete);
    }

    public Tenant getTenant(Integer id) {
        return tenantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found!"));
    }

    public Tenant changeRent(Tenant tenant, Integer value) {
        tenant.setValue(value);
        return tenantRepository.save(tenant);
    }

    public Tenant setToNotPaid(Tenant tenant) {
        tenant.setToNotPaid();
        return tenantRepository.save(tenant);
    }

    public Tenant changeDueDate(Tenant tenant, LocalDate dueDate) {
        tenant.setDueDate(dueDate);
        return tenantRepository.save(tenant);
    }

    public Tenant setToPaid(Tenant tenant) {
        tenant.setToPaid();
        putInHistory(tenant);
        return tenantRepository.save(tenant);
    }

    private void putInHistory(Tenant tenant) {
        PaidTenant paidTenant = new PaidTenant(tenant.getName(), tenant.getId(), tenant.getRent(), LocalDate.now());
        historyRepository.save(paidTenant);
    }
}
