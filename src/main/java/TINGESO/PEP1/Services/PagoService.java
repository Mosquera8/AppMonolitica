package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Entities.PorcentajesEntity;
import TINGESO.PEP1.Entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    ProveedorService proveedorService;
    @Autowired
    AcopioService acopioService;
    @Autowired
    PorcentajesService porcentajesService;


    public void generarPlanillaPago(String codigoProveedor) {
        // Obtener los acopios de leche de la última quincena del proveedor por su código identificador
        List<AcopioEntity> acopiosUltimaQuincena = acopioService.quincenaPorProveedor(codigoProveedor,1);

        ProveedorEntity proveedor = proveedorService.obtenerPorCodigo(codigoProveedor);

        PorcentajesEntity porcentajesActual = porcentajesService.
                obtenerPorCodigoYFecha(proveedor.getCode(), LocalDate.now());


        // Calcular la cantidad total de leche enviada por el proveedor
        double cantidadTotalLeche = acopiosUltimaQuincena.stream()
                .mapToDouble(AcopioEntity::getKg_leche)
                .sum();

        // Asignar el pago por kilo de leche según la categoría del proveedor
        double pagoPorKilo = obtenerPagoPorKilo(proveedor);

        // Calcular el monto total a pagar
        double pagoPorLeche = cantidadTotalLeche * pagoPorKilo;
        double pagoPorGrasa = bonificacionPorGrasa(porcentajesActual)*cantidadTotalLeche;
        double pagoPorSolidos = bonificacionPorSolidos(porcentajesActual)*cantidadTotalLeche;
        double montoTotal = pagoPorLeche + pagoPorGrasa + pagoPorSolidos;
        double bonificacionFrecuencia = calcularBonificacion(acopiosUltimaQuincena)*montoTotal;

        double montoTotalBonificado = montoTotal + bonificacionFrecuencia;

        double descuentoLeche = descuentoVariacionL(codigoProveedor, cantidadTotalLeche);




        // Generar la planilla de pago
        System.out.println("Planilla de Pago para el Proveedor: " + codigoProveedor);
        System.out.println("Cantidad de leche enviada: " + cantidadTotalLeche + " kilos");
        System.out.println("Pago por kilo de leche: $" + pagoPorKilo);
        System.out.println("Monto total a pagar: $" + montoTotalBonificado);
    }

    private double obtenerPagoPorKilo(ProveedorEntity proveedor) {
        String categoria = proveedor.getCategoria();
        double pagoPorKilo = 0.0;
        if ("A".equals(categoria)) {
            pagoPorKilo = 700.0;
        } else if ("B".equals(categoria)) {
            pagoPorKilo = 550.0;
        } else if ("C".equals(categoria)) {
            pagoPorKilo = 400.0;
        } else if ("D".equals(categoria)) {
            pagoPorKilo = 250.0;
        }
        return pagoPorKilo;
    }

    private double bonificacionPorGrasa(PorcentajesEntity porcentajes){
        double grasas = porcentajes.getGrasas();
        if(grasas >= 46.0){
            return 120.0;
        }else if(grasas >= 21.0) {
            return 80.0;
        }else{
            return 30.0;
        }
    }

    private double bonificacionPorSolidos(PorcentajesEntity porcentajes){
        double solidos = porcentajes.getGrasas();
        if(solidos >= 36.0){
            return 150.0;
        }else if(solidos >= 19.0) {
            return 95.0;
        }else if(solidos >= 8.0) {
            return -90.0;
        }else {
            return -130.0;
        }
    }
    private double calcularBonificacion(List<AcopioEntity> acopiosQuincenales) {

        int enviosManana = 0;
        int enviosTarde = 0;

        for (AcopioEntity acopio : acopiosQuincenales) {
            if (acopio.getTurno().equals("M")) {
                enviosManana++;
            } else if (acopio.getTurno().equals("T")) {
                enviosTarde++;
            }
        }

        if (enviosManana > 10 && enviosTarde > 10) {
            return 0.2; // Bonificación del 20%
        } else if (enviosManana > 10) {
            return 0.12; // Bonificación del 12%
        } else if (enviosTarde > 10) {
            return 0.08; // Bonificación del 8%
        } else {
            return 0.0; // Sin bonificación
        }
    }

    private double descuentoVariacionL(String proveedor,Double cantidadActual){
        double variacion = variacionLeche(proveedor,cantidadActual);
        if(variacion <= -0.46){
            return 0.3;
        }else if(variacion <= -0.26){
            return 0.15;
        }else if(variacion <= -0.09) {
            return 0.07;
        }else{
            return 0.0;
        }
    }
    private double variacionLeche(String proveedor,Double cantidadActual){
        List<AcopioEntity> acopiosQuincenaAnterior = acopioService.quincenaPorProveedor(proveedor,2);
        double cantidadLecheAnterior = acopiosQuincenaAnterior.stream()
                .mapToDouble(AcopioEntity::getKg_leche)
                .sum();
        return (cantidadActual - cantidadLecheAnterior)/cantidadLecheAnterior;
    }

}
