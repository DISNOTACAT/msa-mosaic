package org.mosaic.auth.company.presentations.controller;

import static org.mosaic.auth.libs.util.ApiResponseUtil.success;

import lombok.RequiredArgsConstructor;
import org.mosaic.auth.company.application.dtos.CompanyResponse;
import org.mosaic.auth.company.application.service.CompanyCommandService;
import org.mosaic.auth.company.presentations.dtos.CreateCompanyRequest;
import org.mosaic.auth.company.presentations.dtos.UpdateCompanyHubIdRequest;
import org.mosaic.auth.company.presentations.dtos.UpdateCompanyRequest;
import org.mosaic.auth.libs.security.entity.CustomUserDetails;
import org.mosaic.auth.libs.util.ApiResponseUtil.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/admin/company")
@RequiredArgsConstructor
public class CompanyAdminController {

    private final CompanyCommandService companyCommandService;

    @PostMapping
    public ResponseEntity<ApiResult<CompanyResponse>> createCompany(
        @RequestBody CreateCompanyRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails){

        return new ResponseEntity<>(success(
            companyCommandService.createCompany(request.toDTO(), userDetails)),
            HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_COMPANY')")
    public ResponseEntity<ApiResult<CompanyResponse>> updateCompany(
        @RequestBody UpdateCompanyRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails){

        return new ResponseEntity<>(success(
            companyCommandService.updateCompany(request.toDTO(), userDetails)),
            HttpStatus.OK);
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_COMPANY')")
    public ResponseEntity<ApiResult<CompanyResponse>> updateCompanyHubId(
        @RequestBody UpdateCompanyHubIdRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails){

        return new ResponseEntity<>(success(
            companyCommandService.updateCompanyHubId(request.toDTO(), userDetails)),
            HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<ApiResult<String>> deleteCompany(
        @PathVariable String companyId,
        @AuthenticationPrincipal CustomUserDetails userDetails){

        companyCommandService.delete(companyId, userDetails);

        return new ResponseEntity<>(success("Delete Company Success"),
            HttpStatus.OK);
    }

}
