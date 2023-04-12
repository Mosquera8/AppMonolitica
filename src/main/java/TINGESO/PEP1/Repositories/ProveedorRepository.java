package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.ProveedorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Integer> {

}
