package space.uselessidea.uibackend.api.config.security;

public interface User {

  Long getUserId();

  String getCorpName();

  String getUserName();

  Long getCorpId();

  default String getJwtAccessToken() {
    return null;
  }

}
