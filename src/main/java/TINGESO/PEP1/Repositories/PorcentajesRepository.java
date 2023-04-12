package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.PorcentajesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorcentajesRepository extends CrudRepository<PorcentajesEntity, Integer> {
}
