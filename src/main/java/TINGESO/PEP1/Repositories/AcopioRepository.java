package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.AcopioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcopioRepository extends CrudRepository<AcopioEntity, Integer> {
    @Query("SELECT a FROM AcopioEntity a WHERE a.fecha BETWEEN :inicio AND :fin")
    List<AcopioEntity> findAcopiosBetweenDates(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}

