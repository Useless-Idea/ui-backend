package space.uselessidea.uibackend.domain.token.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EsiTokenDto {

  private Long id;

  private Instant expDate;

  private String refreshToken;

  private String jwt;


}
