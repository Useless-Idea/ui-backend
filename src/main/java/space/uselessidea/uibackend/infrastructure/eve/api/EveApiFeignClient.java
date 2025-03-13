package space.uselessidea.uibackend.infrastructure.eve.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;

@FeignClient(value = "eveApiFeignClient", url = "https://esi.evetech.net/latest")
public interface EveApiFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/characters/{characterId}/?datasource=tranquility")
  CharacterPublicData getCharacterPublicData(@PathVariable("characterId") Long characterId);

  /**
   * Corporation data
   */
  @RequestMapping(method = RequestMethod.GET, value = "/corporations/{corporation_id}/?datasource=tranquility")
  CorporationPublicData getCorporationPublicData(@PathVariable("corporation_id") Long corporationId);


}
