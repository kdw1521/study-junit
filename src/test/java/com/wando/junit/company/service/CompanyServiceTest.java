package com.wando.junit.company.service;

import com.wando.junit.company.domain.Company;
import com.wando.junit.company.domain.CompanyRepository;
import com.wando.junit.company.util.AlarmPush;
import com.wando.junit.company.web.dto.request.CompanySaveRequestDTO;
import com.wando.junit.company.web.dto.response.CompanyListResponseDTO;
import com.wando.junit.company.web.dto.response.CompanyResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock // 가짜 메모리 환경에 로드
    private CompanyRepository companyRepository;

    @Mock
    private AlarmPush alarmPush;

    @InjectMocks // 가짜 메모리 환경에 로드된 Mock 객체들을 inject 함
    private CompanyService companyService;

    @Test
    @DisplayName("회사등록")
    public void save_company_test() {
        // given
        CompanySaveRequestDTO dto = new CompanySaveRequestDTO();
        dto.setCompanyName("wando_company");
        dto.setCeoName("wando");

        // stub
        when(companyRepository.save(any())).thenReturn(dto.toEntity());
        when(alarmPush.push()).thenReturn(true);

        // when
        CompanyResponseDTO companyResponseDTO = companyService.saveCompany(dto);

        //then
        assertThat(companyResponseDTO.getCompanyName()).isEqualTo(dto.getCompanyName());
        assertThat(companyResponseDTO.getCeoName()).isEqualTo(dto.getCeoName());
    }

    @Test
    @DisplayName("회사 전체 조회")
    public void find_all_company_test() {
        // given

        // stub
        List<Company> companyList = Arrays.asList(
                new Company().builder().id(1L).companyName("wando_company_1").ceoName("wando_1").build(),
                new Company().builder().id(2L).companyName("wando_company_2").ceoName("wando_2").build()
        );
        when(companyRepository.findAll()).thenReturn(companyList);

        // when
        CompanyListResponseDTO companyListResponseDTO = companyService.findAllCompany();

        // then
        assertThat(companyListResponseDTO.getData().get(0).getCompanyName()).isEqualTo("wando_company_1");
        assertThat(companyListResponseDTO.getData().get(1).getCompanyName()).isEqualTo("wando_company_2");
    }

    @Test
    @DisplayName("회사 한건 조회")
    public void find_one_company_test() {
        // given
        Long id = 1L;
        Company company = Company.builder()
                .id(id)
                .companyName("wando_company")
                .ceoName("wando")
                .build();
        Optional<Company> companyOptional = Optional.of(company);

        // stub
        when(companyRepository.findById(id)).thenReturn(companyOptional);

        // when
        CompanyResponseDTO companyResponseDTO = companyService.findOneCompany(id);

        // then
        assertThat(companyResponseDTO.getCompanyName()).isEqualTo(company.getCompanyName());
    }

    @Test
    @DisplayName("회사 수정")
    public void update_company_test() {
        // given
        Long id = 1L;
        CompanySaveRequestDTO companySaveRequestDTO = new CompanySaveRequestDTO();
        companySaveRequestDTO.setCompanyName("wando_company");
        companySaveRequestDTO.setCeoName("wando");

        // stub
        Company company = Company.builder().id(id).companyName("wando_company_origin").ceoName("wando_origin").build();
        Optional<Company> companyOptional = Optional.of(company);
        when(companyRepository.findById(id)).thenReturn(companyOptional);

        // when
        CompanyResponseDTO companyResponseDTO = companyService.updateCompany(id, companySaveRequestDTO);

        // then
        assertThat(companyResponseDTO.getCompanyName()).isEqualTo(companySaveRequestDTO.getCompanyName());
    }
}
