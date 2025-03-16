package space.uselessidea.uibackend.infrastructure.eve.api;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.SkillsApiResponse;
import space.uselessidea.uibackend.infrastructure.eve.api.data.TypeData;

@FeignClient(value = "eveApiFeignClient", url = "https://esi.evetech.net/latest")
public interface EveApiFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/characters/{characterId}/?datasource=tranquility")
  CharacterPublicData getCharacterPublicData(@PathVariable("characterId") Long characterId);

  /**
   * Get Type information by id
   *
   * @param typeId type id
   * @return Type Information
   */
  @RequestMapping(method = RequestMethod.GET, value = "/universe/types/{typeId}/?datasource=tranquility&language=en")
  @Cacheable(value = "Type", key = "#typeId")
  TypeData getType(@PathVariable("typeId") Long typeId);

  /**
   * Get User Skill
   */
  @RequestMapping(method = RequestMethod.GET,
      value = "/characters/{characterId}/skills/?datasource=tranquility&token={jwtToken}")
  SkillsApiResponse getCharacterSkills(@PathVariable("characterId") Long characterId,
      @PathVariable("jwtToken") String token);

  /**
   * Corporation data
   */
  @RequestMapping(method = RequestMethod.GET, value = "/corporations/{corporation_id}/?datasource=tranquility")
  CorporationPublicData getCorporationPublicData(@PathVariable("corporation_id") Long corporationId);


}
