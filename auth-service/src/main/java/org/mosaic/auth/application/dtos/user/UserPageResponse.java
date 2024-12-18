package org.mosaic.auth.application.dtos.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.mosaic.auth.domain.model.user.User;
import org.springframework.data.domain.Page;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserPageResponse {

  private UserPage userPage;

  public static UserPageResponse of(Page<User> userPage) {
    return UserPageResponse.builder()
        .userPage(new UserPage(userPage))
        .build();
  }

}
