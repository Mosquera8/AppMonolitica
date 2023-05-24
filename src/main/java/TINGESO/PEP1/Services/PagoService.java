package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Entities.PlanillaEntity;
import TINGESO.PEP1.Entities.PorcentajesEntity;
import TINGESO.PEP1.Entities.ProveedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PagoService {
    @Autowired
    ProveedorService proveedorService;
    @Autowired
    AcopioService acopioService;
    @Autowired
    PorcentajesService porcentajesService;

    @Autowired
    PlanillaService planillaService;
    public PlanillaEntity generarPlanillaPago(String codigoProveedor) {
        // Obtener los acopios de leche de la última quincena del proveedor por su código identificador
        List<AcopioEntity> acopiosUltimaQuincena = acopioService.quincenaPorProveedor(codigoProveedor,1);

        ProveedorEntity proveedor = proveedorService.obtenerPorCodigo(codigoProveedor);

        PorcentajesEntity porcentajesActual = porcentajesService.
                obtenerPorCodigoYFecha(proveedor.getCode(), LocalDate.now());


        // Calcular la cantidad total de leche enviada por el proveedor
        double cantidadTotalLeche = acopiosUltimaQuincena.stream()
                .mapToDouble(AcopioEntity::getKg_leche)
                .sum();

        double promedioDiario = cantidadTotalLeche/15;
        Integer dias = obtenerDias(acopiosUltimaQuincena);
        // Asignar el pago por kilo de leche según la categoría del proveedor
        double pagoPorKilo = obtenerPagoPorKilo(proveedor);

        // Calcular el monto total a pagar
        double pagoPorLeche = cantidadTotalLeche * pagoPorKilo;
        double pagoPorGrasa = bonificacionPorGrasa(porcentajesActual)*cantidadTotalLeche;
        double pagoPorSolidos = bonificacionPorSolidos(porcentajesActual)*cantidadTotalLeche;
        double montoTotal = pagoPorLeche + pagoPorGrasa + pagoPorSolidos;
        double bonificacionFrecuencia = calcularBonificacion(acopiosUltimaQuincena)*montoTotal;

        double montoTotalBonificado = montoTotal + bonificacionFrecuencia;

        double variaconLeche = variacionLeche(codigoProveedor, cantidadTotalLeche);
        double variacionGrasa = variacionGrasa(codigoProveedor, porcentajesActual.getGrasas());
        double variacionSolidos = variacionSolidos(codigoProveedor, porcentajesActual.getSolidos());

        double descuentoLeche = descuentoVariacionKilos(codigoProveedor, cantidadTotalLeche);
        double descuentoGrasa = descuentoVariacionGrasa(codigoProveedor, porcentajesActual.getGrasas());
        double descuentoSolidos = descuentoVariacionSolidos(codigoProveedor, porcentajesActual.getSolidos());


        double pagoTotal = montoTotalBonificado - descuentoLeche - descuentoGrasa - descuentoSolidos;



        double pagoFinal;
        if(proveedor.getRetencion() == 1){
            pagoFinal = pagoFinal(pagoTotal);
        }else {
            pagoFinal = pagoTotal;
        }
        double retencion = pagoFinal - pagoTotal;


        return planillaService.crearPlanilla(LocalDate.now(),codigoProveedor,
                proveedor.getNombre(), cantidadTotalLeche, dias, promedioDiario, variaconLeche,
                porcentajesActual.grasas, variacionGrasa, porcentajesActual.solidos, variacionSolidos,
                pagoPorLeche, pagoPorGrasa, pagoPorSolidos, bonificacionFrecuencia, descuentoLeche,
                descuentoGrasa,descuentoSolidos, pagoTotal, retencion, pagoFinal);
    }

    public double obtenerPagoPorKilo(ProveedorEntity proveedor) {
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

    public double bonificacionPorGrasa(PorcentajesEntity porcentajes){
        double grasas = porcentajes.getGrasas();
        if(grasas >= 46.0){
            return 120.0;
        }else if(grasas >= 21.0) {
            return 80.0;
        }else{
            return 30.0;
        }
    }

    public double bonificacionPorSolidos(PorcentajesEntity porcentajes){
        double solidos = porcentajes.getSolidos();
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
    public double calcularBonificacion(List<AcopioEntity> acopiosQuincenales) {

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

    public double descuentoVariacionKilos(String proveedor,Double cantidadActual){
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
    public double variacionLeche(String proveedor,Double cantidadActual){
        List<AcopioEntity> acopiosQuincenaAnterior = acopioService.quincenaPorProveedor(proveedor,2);
        double cantidadLecheAnterior = acopiosQuincenaAnterior.stream()
                .mapToDouble(AcopioEntity::getKg_leche)
                .sum();
        return (cantidadActual - cantidadLecheAnterior)/cantidadLecheAnterior;
    }

    public double descuentoVariacionGrasa(String proveedor,Integer cantidadActual){
        double variacion = variacionGrasa(proveedor,cantidadActual);
        if(variacion <= -0.41){
            return 0.3;
        }else if(variacion <= -0.26){
            return 0.20;
        }else if(variacion <= -0.16) {
            return 0.12;
        }else{
            return 0.0;
        }
    }

    public double variacionGrasa(String proveedor,Integer cantidadActual){
        LocalDate quincenaAnterior = LocalDate.now().minusDays(15);
        PorcentajesEntity porentajeQuinAnt = porcentajesService.obtenerPorCodigoYFecha(proveedor,quincenaAnterior);
        double grasaAnterior = porentajeQuinAnt.getGrasas();
        return (cantidadActual - grasaAnterior)/grasaAnterior;
    }

    public double descuentoVariacionSolidos(String proveedor,Integer cantidadActual){
        double variacion = variacionSolidos(proveedor,cantidadActual);
        if(variacion <= -0.36){
            return 0.45;
        }else if(variacion <= -0.13){
            return 0.27;
        }else if(variacion <= -0.07) {
            return 0.18;
        }else{
            return 0.0;
        }
    }

    public double variacionSolidos(String proveedor,Integer cantidadActual){
        LocalDate quincenaAnterior = LocalDate.now().minusDays(15);
        PorcentajesEntity porentajeQuinAnt = porcentajesService.obtenerPorCodigoYFecha(proveedor,quincenaAnterior);
        double solidosAnterior = porentajeQuinAnt.getSolidos();
        return (cantidadActual - solidosAnterior)/solidosAnterior;
    }

    public double pagoFinal(double pago){
        if(pago > 950000){
            return pago - (pago * 0.13);
        }
        return pago;
    }

    public Integer obtenerDias(List<AcopioEntity> quincena){
        ArrayList<LocalDate> dias = new ArrayList<>();
        for(AcopioEntity acopio : quincena){
            dias.add(acopio.getFecha());
        }
        Set<LocalDate> diasUnicos = new HashSet<>(dias);
        return diasUnicos.size();
    }
}
