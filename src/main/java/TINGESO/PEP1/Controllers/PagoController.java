package TINGESO.PEP1.Controllers;

import TINGESO.PEP1.Entities.PlanillaEntity;
import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Services.PagoService;
import TINGESO.PEP1.Services.PlanillaService;
import TINGESO.PEP1.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping
public class PagoController {
    @Autowired
    PagoService pagoService;

    @Autowired
    PlanillaService planillaService;

    @Autowired
    ProveedorService proveedorService;
    @GetMapping("/planilla")
    public String pago(Model model){
        List<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        if(proveedores.isEmpty()){
            model.addAttribute("error","No hay proveedores registrados");
        }else{
            model.addAttribute("proveedores",proveedores);
        }
        return "planilla";
    }



    @GetMapping("/obtener-planilla")
    public String obtenerPlanilla(@RequestParam("codigo") String codigo, Model model){
        PlanillaEntity planilla = pagoService.generarPlanillaPago(codigo);
        if(planilla == null){
            model.addAttribute("error","No se encontro el proveedor");
        }else{
            model.addAttribute("planilla",planilla);
        }
        return "show-planilla";
    }
}
