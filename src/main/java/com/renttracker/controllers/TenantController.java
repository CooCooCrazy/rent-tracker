package com.renttracker.controllers;

import com.renttracker.services.DownloadServices;
import com.renttracker.services.PhotoServices;
import com.renttracker.services.TenantService;
import com.renttracker.domain.PaidTenant;
import com.renttracker.domain.Rent;
import com.renttracker.domain.Tenant;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("tenants")
public class TenantController {
    private final TenantService tenantService;
    private final PhotoServices photoServices;
    private final DownloadServices downloadServices;

    public TenantController(TenantService tenantService, PhotoServices photoServices, DownloadServices downloadServices) {
        this.tenantService = tenantService;
        this.photoServices = photoServices;
        this.downloadServices = downloadServices;
    }

    @PostMapping
    public void addTenant(@Valid @RequestBody Tenant tenant) {
        tenantService.addTenant(tenant);
    }

    @GetMapping
    public List<Tenant> getTenants() {
        return tenantService.getTenants();
    }

    @DeleteMapping("{id}/delete")
    public void removeTenant(@PathVariable Integer id) {
        tenantService.removeTenant(id);
    }

    @PutMapping("{id}/change-rent")
    public Tenant changeRent(@PathVariable Integer id, @RequestBody Rent newRent) {
        Tenant tenant = tenantService.getTenant(id);
        return tenantService.changeRent(tenant, newRent.getValue());
    }

    @PutMapping("{id}/change-due-date")
    public Tenant changeDueDate(@PathVariable Integer id, @RequestBody Rent newDueDate) {
        Tenant tenant = tenantService.getTenant(id);
        return tenantService.changeDueDate(tenant, newDueDate.getDueDate());
    }

    @PutMapping("{id}/set-to-paid")
    public Tenant setToPaid(@PathVariable Integer id) {
        Tenant tenant = tenantService.getTenant(id);
        return tenantService.setToPaid(tenant);
    }

    @PutMapping("{id}/restart")
    public Tenant restart(@PathVariable Integer id, @RequestBody Rent dueDate) {
        Tenant tenant = tenantService.getTenant(id);
        tenantService.changeDueDate(tenant, dueDate.getDueDate());
        tenantService.setToNotPaid(tenant);
        return tenant;
    }

    @GetMapping("payment-history")
    public List<PaidTenant> getHistory() {
        return tenantService.getHistory();
    }

    @PostMapping("{id}/photo")
    public void uploadPhoto(@PathVariable Integer id, MultipartFile file) {
        photoServices.uploadPhoto(id, file);
    }

    @GetMapping("download-history")
    public ResponseEntity<byte[]> downloadHistory() {
        String fileName = "TenantHistory";
        byte[] fileData = downloadServices.load();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payment-history.csv").contentType(MediaType.parseMediaType("text/csv")).body(fileData);
    }
}
