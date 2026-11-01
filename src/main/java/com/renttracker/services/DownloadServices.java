package com.renttracker.services;

import com.renttracker.domain.PaidTenant;
import com.renttracker.repositories.HistoryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class DownloadServices {
    private final HistoryRepository historyRepository;

    public DownloadServices(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public byte[] load() {
        List<PaidTenant> paidTenants = historyRepository.findAll();
        return paidTenantsToCsv(paidTenants);
    }

    private byte[] paidTenantsToCsv(List<PaidTenant> paidTenants) {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader("Name", "Value", "PaidDate", "DueDate")
                .get();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

            for (PaidTenant p : paidTenants) {
                csvPrinter.printRecord(p.getName(), p.getRent().getValue(), p.getPaidDate(), p.getRent().getDueDate());
            }

            csvPrinter.flush();
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
