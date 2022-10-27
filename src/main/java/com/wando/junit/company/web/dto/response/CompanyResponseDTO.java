package com.wando.junit.company.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CompanyResponseDTO {
    private Long id;
    private String companyName;
    private String ceoName;

    @Builder
    public CompanyResponseDTO(Long id, String companyName, String ceoName) {
        this.id = id;
        this.companyName = companyName;
        this.ceoName = ceoName;
    }
}
