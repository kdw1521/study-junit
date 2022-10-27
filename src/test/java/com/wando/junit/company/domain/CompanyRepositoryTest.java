package com.wando.junit.company.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("dev")
@DataJpaTest // DB 관련 컴포넌트들만 메모리에 로드
@DisplayName("Company DB TEST")
public class CompanyRepositoryTest {

    @Autowired // @Autowired 를 사용하는 이유 -https://junit.org/junit5/docs/current/user-guide/#writing-tests-dependency-injection
    private CompanyRepository companyRepository;

    @BeforeEach // transaction 이 어떻게 범위를 잡는지 확인
    public void init_data() {
        String companyName = "init_wando_company", ceoName = "init_dowan";
        Company company = Company.builder().companyName(companyName).ceoName(ceoName).build();
        companyRepository.save(company);
    }

    @Test
    @DisplayName("회사 등록")
    public void save_test() {
        // given - 데이터 준비
        String companyName = "wando_company", ceoName = "dowan";
        Company company = Company.builder()
                                .companyName(companyName)
                                .ceoName(ceoName)
                                .build();

        // when - 테스트 실행
        Company companyPersistence = companyRepository.save(company);

        // then - 검증
        assertThat(companyPersistence.getCompanyName()).isEqualTo(companyName);
        assertThat(companyPersistence.getCeoName()).isEqualTo(ceoName);
    }

    @Test
    @DisplayName("회사 전체 조회")
    public void find_all_test() {
        // given
        String companyName = "init_wando_company", ceoName = "init_dowan";

        // when
        List<Company> companyPersistence = companyRepository.findAll();

        // then
        assertThat(companyPersistence.get(0).getCompanyName()).isEqualTo(companyName);
        assertThat(companyPersistence.get(0).getCeoName()).isEqualTo(ceoName);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("회사 한건 조회")
    public void find_one_test() {
        // given
        String companyName = "init_wando_company", ceoName = "init_dowan";
        Long id = 1L;

        // when
        Company companyPersistence = companyRepository.findById(id).get();

        // then
        assertThat(companyPersistence.getCompanyName()).isEqualTo(companyName);
        assertThat(companyPersistence.getCeoName()).isEqualTo(ceoName);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("회사 삭제")
    public void delete_test() {
        // given
        Long id = 1L;

        // when
        companyRepository.deleteById(id);

        // then
        assertFalse(companyRepository.findById(id).isPresent());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    @DisplayName("회사 수졍")
    public void update_test() {
        // given
        Long id = 1L;
        String companyName = "update_wando_company", ceoName = "update_dowan";
        Company company = Company.builder()
                                 .id(id)
                                 .companyName(companyName)
                                 .ceoName(ceoName)
                                 .build();

        // when
        Company companyPersistence = companyRepository.save(company);

        // then
        assertThat(companyPersistence.getId()).isEqualTo(id);
        assertThat(companyPersistence.getCompanyName()).isEqualTo(companyName);
        assertThat(companyPersistence.getCeoName()).isEqualTo(ceoName);
    }
}
