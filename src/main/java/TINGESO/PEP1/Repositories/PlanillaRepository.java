package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.PlanillaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaRepository extends CrudRepository<PlanillaEntity, Integer> {
}
