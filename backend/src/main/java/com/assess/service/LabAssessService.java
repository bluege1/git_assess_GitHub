package com.assess.service;

import com.assess.dto.LabAssessRequest;
import com.assess.dto.LabAssessResponse;
import com.assess.entity.LabAssess;
import com.assess.repository.LabAssessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LabAssessService {

    private final LabAssessRepository repository;

    public LabAssessService(LabAssessRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<LabAssessResponse> listAll() {
        return repository.findAllByOrderByIdDesc().stream()
                .map(LabAssessResponse::from)
                .toList();
    }

    @Transactional
    public LabAssessResponse create(LabAssessRequest request) {
        LabAssess entity = new LabAssess();
        entity.setName(request.getName().trim());
        entity.setAssessmentDirection(request.getAssessmentDirection());
        entity.setFrontendResult(defaultInt(request.getFrontendResult()));
        entity.setBackendResult(defaultInt(request.getBackendResult()));
        entity.setDataManagementResult(defaultInt(request.getDataManagementResult()));
        entity.setAssessmentTime(parseAssessmentTime(request.getAssessmentTime()));
        entity.setAssessor(blankToNull(request.getAssessor()));

        return LabAssessResponse.from(repository.save(entity));
    }

    private static int defaultInt(Integer value) {
        return value != null ? value : 0;
    }

    private static String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    /** 解析前端 datetime-local 字符串，不做时区换算 */
    private static LocalDateTime parseAssessmentTime(String value) {
        if (value == null || value.isBlank()) {
            return LocalDateTime.now();
        }
        String normalized = value.trim().replace('T', ' ');
        if (normalized.length() == 16) {
            normalized += ":00";
        }
        return LocalDateTime.parse(normalized, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
