package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.AcopioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcopioRepository extends CrudRepository<AcopioEntity, Integer> {
}
