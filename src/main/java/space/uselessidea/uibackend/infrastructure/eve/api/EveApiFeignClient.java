package space.uselessidea.uibackend.infrastructure.eve.api;

import java.util.Set;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CharacterPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.CorporationPublicData;
import space.uselessidea.uibackend.infrastructure.eve.api.data.ItemTypeApiResponse;
import space.uselessidea.uibackend.infrastructure.eve.api.data.SkillsApiResponse;

@FeignClient(value = "eveApiFeignClient", url = "https://esi.evetech.net/latest")
public interface EveApiFeignClient {

  @RequestMapping(method = RequestMethod.GET, value = "/characters/{characterId}/?datasource=tranquility")
  CharacterPublicData getCharacterPublicData(@PathVariable("characterId") Long characterId);


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

  @RequestMapping(method = RequestMethod.GET, value = "/universe/types/?datasource=tranquility&page={page}")
  ResponseEntity<Set<Long>> getItemTypeIds(@PathVariable("page") Integer page);


  /**
   * Get Item Type by ID
   *
   * @param itemTypeId ID
   * @return Item Type
   */
  @RequestMapping(method = RequestMethod.GET, value = "/universe/types/{type_id}/?datasource=tranquility&language=en")
  @Cacheable(value = "type", key = "#itemTypeId")
  ItemTypeApiResponse getItemByItemTypeId(@PathVariable("type_id") Long itemTypeId);
}
