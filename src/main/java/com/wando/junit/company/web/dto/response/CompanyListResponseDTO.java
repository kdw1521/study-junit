package com.wando.junit.company.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CompanyListResponseDTO {
    List<CompanyResponseDTO> data;

    @Builder
    public CompanyListResponseDTO(List<CompanyResponseDTO> companyList) {
        this.data = companyList;
    }
}
