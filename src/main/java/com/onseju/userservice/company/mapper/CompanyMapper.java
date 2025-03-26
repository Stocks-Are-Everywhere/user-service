package com.onseju.userservice.company.mapper;

import org.springframework.stereotype.Component;

import com.onseju.orderservice.company.controller.response.CompanySearchResponse;
import com.onseju.orderservice.company.domain.Company;

@Component
public class CompanyMapper {

    public CompanySearchResponse toCompanySearchResponse(Company company) {
        return new CompanySearchResponse(
                company.getIsuNm(),
                company.getIsuSrtCd(),
                company.getIsuAbbrv(),
                company.getIsuEngNm(),
                company.getKindStkcertTpNm());
    }
}
