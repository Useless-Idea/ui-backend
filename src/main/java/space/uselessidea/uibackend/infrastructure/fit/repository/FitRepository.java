package space.uselessidea.uibackend.infrastructure.fit.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.uselessidea.uibackend.infrastructure.fit.persistence.Fit;

@Repository
public interface FitRepository extends JpaRepository<Fit, UUID> {

  @Query(
      value =
          """
      SELECT *
      FROM fit f
      WHERE
          (:fitName IS NULL OR :fitName = '' OR f.fit_name = :fitName)
      AND (
          :pilotNames IS NULL
          OR EXISTS (
              SELECT 1
              FROM jsonb_array_elements(f.pilots->'active') p
              WHERE (p->>'name')::text IN (:pilotNames)
          )
          OR EXISTS (
              SELECT 1
              FROM jsonb_array_elements(f.pilots->'inactive') p
              WHERE (p->>'name')::text IN (:pilotNames)
          )
      )
      AND (
          :doctrines IS NULL
          OR EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(f.doctrines) d
              WHERE d IN (:doctrines)
          )
      )
      """,
      countQuery =
          """
          SELECT count(*)
          FROM fit f
          WHERE
              (:fitName IS NULL OR :fitName = '' OR f.fit_name = :fitName)
          AND (
              :pilotNames IS NULL
              OR EXISTS (
                  SELECT 1
                  FROM jsonb_array_elements(f.pilots->'active') p
                  WHERE (p->>'name')::text IN (:pilotNames)
              )
              OR EXISTS (
                  SELECT 1
                  FROM jsonb_array_elements(f.pilots->'inactive') p
                  WHERE (p->>'name')::text IN (:pilotNames)
              )
          )
          AND (
              :doctrines IS NULL
              OR EXISTS (
                  SELECT 1
                  FROM jsonb_array_elements_text(f.doctrines) d
                  WHERE d IN (:doctrines)
              )
          )
          """,
      nativeQuery = true)
  Page<Fit> findFits(
      @Param("fitName") String fitName,
      @Param("pilotNames") List<String> pilotNames,
      @Param("doctrines") List<String> doctrines,
      Pageable pageable);
}
