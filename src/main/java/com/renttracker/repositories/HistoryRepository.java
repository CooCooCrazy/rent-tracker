package com.renttracker.repositories;

import com.renttracker.domain.PaidTenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<PaidTenant, Integer> {
}
