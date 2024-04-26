package com.harsh.companyms.company.service;



import com.harsh.companyms.company.Company;
import com.harsh.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {

    List<Company> findAllCompanies();

    void createCompany(Company company);
    boolean updateCompany(Company company, Long id);

    boolean deleteCompanyById(Long id);

    Company getCompanyById(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}
