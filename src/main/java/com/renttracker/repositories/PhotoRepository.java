package com.renttracker.repositories;

import com.renttracker.domain.TenantPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<TenantPhoto, Integer> {
    // identifiers here mean a thing, find - by - tenantId
    Optional<TenantPhoto> findByTenantId(Integer tenantId);
}
