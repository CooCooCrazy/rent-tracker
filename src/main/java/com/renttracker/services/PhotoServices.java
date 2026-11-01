package com.renttracker.services;

import com.renttracker.domain.TenantPhoto;
import com.renttracker.repositories.PhotoRepository;
import com.renttracker.repositories.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class PhotoServices {
    private final PhotoRepository photoRepository;
    private final TenantRepository tenantRepository;

    public PhotoServices(PhotoRepository photoRepository, TenantRepository tenantRepository) {                                    // overwrite if tenant photo exist for that tenant, if not then new
        this.photoRepository = photoRepository;                                                                                   // same tenant id overwrite, no photo new
        this.tenantRepository = tenantRepository;
    }

    public void uploadPhoto(Integer tenantId, MultipartFile file) {
        if (!(tenantRepository.existsById(tenantId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found!");
        }

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file uploaded");
        }

        String type = file.getContentType();
        if (!"image/jpeg".equals(type) && !"image/png".equals(type)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only JPEG or PNG images are allowed!");
        }

        TenantPhoto photo = photoRepository.findByTenantId(tenantId)
                .orElse(new TenantPhoto());

        photo.setTenantId(tenantId);
        photo.setFileType(file.getContentType());
        try {
            photo.setData(file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read the uploaded file");
        }

        photoRepository.save(photo);
    }
}
