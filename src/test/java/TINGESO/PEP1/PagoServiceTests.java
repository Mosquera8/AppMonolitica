package TINGESO.PEP1;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Entities.PlanillaEntity;
import TINGESO.PEP1.Entities.PorcentajesEntity;
import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Repositories.AcopioRepository;
import TINGESO.PEP1.Repositories.PlanillaRepository;
import TINGESO.PEP1.Repositories.PorcentajesRepository;
import TINGESO.PEP1.Repositories.ProveedorRepository;
import TINGESO.PEP1.Services.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class PagoServiceTests {

    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    AcopioRepository acopioRepository;
    @Autowired
    PorcentajesRepository porcentajesRepository;
    @Autowired
    PlanillaRepository planillaRepository;

    @Autowired
    ProveedorService proveedorService;
    @Autowired
    AcopioService acopioService;
    @Autowired
    PorcentajesService porcentajesService;
    @Autowired
    PagoService pagoService;
    @Autowired
    PlanillaService planillaService;



    ProveedorEntity proveedorEntity = new ProveedorEntity();
    PorcentajesEntity porcentajesEntity = new PorcentajesEntity();


    @BeforeEach
    void contextLoads() {
        MockitoAnnotations.initMocks(this);
        proveedorEntity.setID(12);
        proveedorEntity.setCode("1234");
        proveedorEntity.setNombre("Juan");
        proveedorEntity.setCategoria("A");
        proveedorEntity.setRetencion(1);

        porcentajesEntity.setID(12);
        porcentajesEntity.setProveedor("1234");
        porcentajesEntity.setGrasas(30);
        porcentajesEntity.setSolidos(20);
        porcentajesEntity.setFecha(LocalDate.now().minusDays(15));

        proveedorRepository.save(proveedorEntity);
        porcentajesRepository.save(porcentajesEntity);


    }
    @ParameterizedTest
    @CsvSource({
            "A, 700",
            "B, 550",
            "C, 400",
            "D, 250"})
    void pruebaObtenerPagoPorKilo(String categoria, double valorEsperado) {
        Integer[] lista = {11,12,13,14,15,16,17,18,19,20};
        proveedorEntity.setCategoria(categoria);
        double resultado = pagoService.obtenerPagoPorKilo(proveedorEntity);
        assertEquals(valorEsperado, resultado);
        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();
    }

    @ParameterizedTest
    @CsvSource({
            "50, 120",
            "25, 80",
            "13, 30"
    })
    void pruebaObtenerPagoPorGrasa(Integer categoria, double valorEsperado) {
        porcentajesEntity.setGrasas(categoria);
        double resultado = pagoService.bonificacionPorGrasa(porcentajesEntity);
        assertEquals(valorEsperado, resultado);
        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();
    }

    @ParameterizedTest
    @CsvSource({
            "50, 150",
            "25, 95",
            "13, -90",
            "3, -130"
    })
    void pruebaObtenerPagoPorSolidos(Integer categoria, double valorEsperado) {

        porcentajesEntity.setSolidos(categoria);
        double resultado = pagoService.bonificacionPorSolidos(porcentajesEntity);
        assertEquals(valorEsperado, resultado);
        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();
    }
    @ParameterizedTest
    @CsvSource({"1000000, 870000", "800000, 800000"})
    void testPagoFinal(double pago, double expected) {
        double result = pagoService.pagoFinal(pago);

        assertEquals(expected, result);
        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();
    }


    @Test
    void testGenerarPlanilla() {
        AcopioEntity acopio1 = new AcopioEntity();
        acopio1.setTurno("M");
        acopio1.setFecha(LocalDate.now().minusDays(2));
        acopio1.setKg_leche(50.0);
        acopio1.setProveedor("1234");
        acopio1.setID(1);

        AcopioEntity acopio2 = new AcopioEntity();
        acopio2.setTurno("T");
        acopio2.setFecha(LocalDate.now().minusDays(2));
        acopio2.setKg_leche(50.0);
        acopio2.setProveedor("1234");
        acopio2.setID(2);


        AcopioEntity acopio3 = new AcopioEntity();
        acopio3.setTurno("T");
        acopio3.setFecha(LocalDate.now().minusDays(10));
        acopio3.setKg_leche(50.0);
        acopio3.setProveedor("1234");
        acopio3.setID(3);

        AcopioEntity acopio4 = new AcopioEntity();
        acopio4.setTurno("T");
        acopio4.setFecha(LocalDate.now().minusDays(12));
        acopio4.setKg_leche(150.0);
        acopio4.setProveedor("1234");
        acopio4.setID(4);

        AcopioEntity acopio5 = new AcopioEntity();
        acopio5.setTurno("T");
        acopio5.setFecha(LocalDate.now().minusDays(20));
        acopio5.setKg_leche(100.0);
        acopio5.setProveedor("1234");
        acopio5.setID(5);



        PorcentajesEntity porcentajes = new PorcentajesEntity();
        porcentajes.setID(13);
        porcentajes.setProveedor("1234");
        porcentajes.setGrasas(60);
        porcentajes.setSolidos(40);
        porcentajes.setFecha(LocalDate.now());

        acopioRepository.save(acopio1);
        acopioRepository.save(acopio2);
        acopioRepository.save(acopio3);
        acopioRepository.save(acopio4);
        acopioRepository.save(acopio5);
        porcentajesRepository.save(porcentajes);



        PlanillaEntity esperado = new PlanillaEntity();
        esperado.setID(null);
        esperado.setFecha(LocalDate.now().toString());
        esperado.setCodigo_Proveedor("1234");
        esperado.setNombre_Proveedor("Juan");
        esperado.setKls_leche("300.0");
        esperado.setDias("3");
        esperado.setPromedio("20.0");
        esperado.setVariacion_leche("2.0");
        esperado.setGrasa("60");
        esperado.setVariacion_grasas("1.0");
        esperado.setSolidos("40");
        esperado.setVariacion_solidos("1.0");
        esperado.setPago_leche("210000.0");
        esperado.setPago_grasa("36000.0");
        esperado.setPago_solidos("45000.0");
        esperado.setBonificacion_frecuencia("0.0");
        esperado.setDescuento_leche("0.0");
        esperado.setDescuento_grasa("0.0");
        esperado.setDescuento_solidos("0.0");
        esperado.setPago_total("291000.0");
        esperado.setMonto_retencion("0.0");
        esperado.setPago_final("291000.0");


        PlanillaEntity resultado = pagoService.generarPlanillaPago("1234");


        assertEquals(esperado, resultado);



        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();

        acopioService.borrarAcopio();
    }

    @ParameterizedTest
    @CsvSource({"10, 0.3",
            "20, 0.20",
            "25, 0.12"})
    void testDescuentoGrasa(Integer actual, Double esperado){

        Double result = pagoService.descuentoVariacionGrasa("1234",actual);
        assertEquals(esperado, result);
        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();
    }

    @ParameterizedTest
    @CsvSource({"10, 0.45",
            "14, 0.27",
            "18, 0.18"})
    void testDescuentoSolido(Integer actual, Double esperado){

        Double result = pagoService.descuentoVariacionSolidos("1234",actual);
        assertEquals(esperado, result);
        proveedorService.borrarTodos();
        porcentajesService.borrarPorcentajes();
    }
}

