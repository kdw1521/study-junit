package com.wando.junit.company.web.dto.request;

import com.wando.junit.company.domain.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanySaveRequestDTO {
    private String companyName;
    private String ceoName;

    public Company toEntity() {
        return Company.builder()
                .companyName(companyName)
                .ceoName(ceoName)
                .build();
    }
}
