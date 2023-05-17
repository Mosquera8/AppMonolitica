package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.ProveedorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends CrudRepository<ProveedorEntity, Integer> {
    @Query("select e from ProveedorEntity e where e.code = :code")
    ProveedorEntity findByCodigo(@Param("code") int code);

}
