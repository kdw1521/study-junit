package com.wando.junit.company.service;

import com.wando.junit.company.domain.Company;
import com.wando.junit.company.domain.CompanyRepository;
import com.wando.junit.company.util.AlarmPush;
import com.wando.junit.company.web.dto.request.CompanySaveRequestDTO;
import com.wando.junit.company.web.dto.response.CompanyListResponseDTO;
import com.wando.junit.company.web.dto.response.CompanyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final AlarmPush alarmPush;

    // 회사 등록
    @Transactional
    public CompanyResponseDTO saveCompany(CompanySaveRequestDTO dto) {
        Company companyPersistence = companyRepository.save(dto.toEntity());
        System.out.println("wando >> " + companyPersistence); // service layer test 에서 stub에서 repository.save 부분 주석하고 값을 확인해보셈.
        if(companyPersistence != null) {
            if(!alarmPush.push()) throw new RuntimeException("푸쉬 알람 실패!");
        }
        return companyPersistence.toDTO();
    }

    // 회사 전체 조회
    public CompanyListResponseDTO findAllCompany() {
        List<CompanyResponseDTO> companyListDTO = companyRepository.findAll()
                                                                    .stream()
                                                                    .map(Company::toDTO)
                                                                    .collect(Collectors.toList());
        CompanyListResponseDTO companyListResponseDTO = CompanyListResponseDTO.builder()
                                                                            .companyList(companyListDTO)
                                                                            .build();
        return companyListResponseDTO;
    }

    // 회사 한건 조회
    public CompanyResponseDTO findOneCompany(Long id) {
        Company companyPersistence = companyRepository.findById(id).orElseThrow();
        return companyPersistence.toDTO();
    }

    // 회사 삭제
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    // 회사 수정
    @Transactional
    public CompanyResponseDTO updateCompany(Long id, CompanySaveRequestDTO dto) {
        Company companyPersistence = companyRepository.findById(id).orElseThrow();
        companyPersistence.update(dto.getCompanyName(), dto.getCeoName());
        return companyPersistence.toDTO();
    }
}
