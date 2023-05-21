package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepo;
    public List<ProveedorEntity> obtenerProveedores(){
        return (List<ProveedorEntity>) proveedorRepo.findAll();
    }

    public void guardarProveedor(ProveedorEntity proveedor){
        proveedorRepo.save(proveedor);
    }

    public ProveedorEntity obtenerPorCodigo(String codigo){
        return proveedorRepo.findByCode(codigo);
    }



}
