package org.mosaic.hub.application.service;

import lombok.RequiredArgsConstructor;
import org.mosaic.hub.application.dtos.CreateHubServiceRequest;
import org.mosaic.hub.application.dtos.CreateHubResponse;
import org.mosaic.hub.application.dtos.UpdateHubServiceRequest;
import org.mosaic.hub.application.dtos.UpdateHubResponse;
import org.mosaic.hub.domain.model.Hub;
import org.mosaic.hub.domain.repository.HubRepository;
import org.mosaic.hub.libs.exception.CustomException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HubCommandService {

  private final HubRepository hubRepository;

  @CachePut(cacheNames = "hubCache", key = "#result.hubUuid")
  public CreateHubResponse createHub(CreateHubServiceRequest request) {
    Hub hub = hubRepository.save(Hub.createHub(
        request.getManagerId(), request.getName(), request.getAddress(),
        request.getLatitude(), request.getLongitude()));

    return CreateHubResponse.from(hub);
  }

  @CachePut(cacheNames = "hubCache", key = "args[0]")
  public UpdateHubResponse updateHub(String hubUuid, UpdateHubServiceRequest request) {
    Hub hub = getHubByUuid(hubUuid);
    hub.update(
        request.getManagerId(),
        request.getName(), request.getAddress(),
        request.getLatitude(), request.getLongitude());

    return UpdateHubResponse.from(hub);
  }

  @CacheEvict(cacheNames = "hubCache", key = "args[1]")
  public void deleteHub(String userUuid, String hubUuid) {
    Hub hub = getHubByUuid(hubUuid);
    hub.softDelete(userUuid);
  }

  private Hub getHubByUuid(String hubUuid) {
    return hubRepository.findByUuid(hubUuid).orElseThrow(
        () -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 허브입니다."));
  }
}