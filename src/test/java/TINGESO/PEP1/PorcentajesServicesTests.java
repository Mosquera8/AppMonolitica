package TINGESO.PEP1;

import TINGESO.PEP1.Entities.PorcentajesEntity;
import TINGESO.PEP1.Repositories.PorcentajesRepository;
import TINGESO.PEP1.Services.PorcentajesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
class PorcentajesServicesTests {
    @Mock
    PorcentajesRepository porcentajesRepository;
    @InjectMocks
    PorcentajesService porcentajesService;
    PorcentajesEntity porcentajes;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        porcentajes = new PorcentajesEntity();
        porcentajes.setID(12);
        porcentajes.setProveedor("1234");
        porcentajes.setFecha(LocalDate.now());
        porcentajes.setGrasas(30);
        porcentajes.setSolidos(30);
    }
    @Test
    void probarGetPorcentajes(){
        when(porcentajesRepository.findAll()).thenReturn(Arrays.asList(porcentajes));
        assertNotNull(porcentajesService.getPorcentajes());
    }


}
