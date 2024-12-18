package org.mosaic.auth.presentation.controller.delivery_driver;

import static org.mosaic.auth.libs.util.ApiResponseUtils.created;
import static org.mosaic.auth.libs.util.ApiResponseUtils.ok;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mosaic.auth.application.dtos.CreateDeliveryDriverResponse;
import org.mosaic.auth.application.dtos.DeliveryDriverResponse;
import org.mosaic.auth.application.service.DeliveryDriverCommandService;
import org.mosaic.auth.libs.security.entity.CustomUserDetails;
import org.mosaic.auth.libs.util.ApiResponseUtils.CommonResponse;
import org.mosaic.auth.presentation.dtos.CreateDeliveryDriverRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryDriverController {

  private final DeliveryDriverCommandService deliveryDriverCommandService;

  @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
  @PostMapping("/api/v1/delivery-drivers")
  public ResponseEntity<CommonResponse<CreateDeliveryDriverResponse>> createDeliveryDriver(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Valid @RequestBody CreateDeliveryDriverRequest request) {
    return created(deliveryDriverCommandService.createDeliveryDriver(userDetails.getUserUuid(), request.toService()));
  }

  @PreAuthorize("hasAnyRole('MASTER')")
  @GetMapping("/api/v1/delivery-drivers/assigned-driver")
  public ResponseEntity<CommonResponse<DeliveryDriverResponse>> assignHubDeliveryDriver() {
    return ok(deliveryDriverCommandService.assignHubDeliveryDriver());
  }

  @PreAuthorize("hasAnyRole('MASTER')")
  @GetMapping("/api/v1/delivery-drivers/company/{hubUuid}/assigned-driver")
  public ResponseEntity<CommonResponse<DeliveryDriverResponse>> assignHubDeliveryDriver(
      @PathVariable String hubUuid) {
    return ok(deliveryDriverCommandService.assignCompanyDeliveryDriver(hubUuid));
  }
}
