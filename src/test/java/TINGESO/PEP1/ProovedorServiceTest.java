package TINGESO.PEP1;

import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Repositories.ProveedorRepository;
import TINGESO.PEP1.Services.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ProovedorServiceTest {
    @Mock
    ProveedorRepository proveedorRepository;
    @InjectMocks
    ProveedorService proveedorService;
    ProveedorEntity proveedor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        proveedor = new ProveedorEntity();
        proveedor.setID(12);
        proveedor.setCode("1234");
        proveedor.setNombre("Juan");
        proveedor.setCategoria("A");
        proveedor.setRetencion(1);
    }
    @Test
    void obtenerEmpleados(){
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        assertNotNull(proveedorService.obtenerProveedores());
    }
    @Test
    void pruebaObtenerPorCodigo(){
        when(proveedorRepository.findByCode("1234")).thenReturn(proveedor);
        assertEquals(proveedor, proveedorService.obtenerPorCodigo("1234"));
    }
}
