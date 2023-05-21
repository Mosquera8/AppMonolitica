package TINGESO.PEP1.Repositories;

import TINGESO.PEP1.Entities.PorcentajesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PorcentajesRepository extends CrudRepository<PorcentajesEntity, Integer> {

    PorcentajesEntity findByProveedorAndFecha(String proveedor, LocalDate fecha);
    PorcentajesEntity findByProveedor(String proveedor);
}
