package org.mosaic.hub.presentation.dtos;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.mosaic.hub.application.dtos.CreateHubServiceRequest;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class CreateHubRequest {

  private Long managerId;

  @NotBlank
  private String name;

  @NotBlank
  private String address;

  @Range(min = 33, max = 39)
  private double latitude;

  @Range(min = 124, max = 132)
  private double longitude;

  public CreateHubServiceRequest toService() {
    return CreateHubServiceRequest.create(
        managerId, name, address, latitude, longitude);
  }
}
