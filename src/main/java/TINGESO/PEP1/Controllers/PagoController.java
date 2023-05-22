package TINGESO.PEP1.Controllers;

import TINGESO.PEP1.Entities.PlanillaEntity;
import TINGESO.PEP1.Services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PagoController {
    @Autowired
    PagoService pagoService;
    @GetMapping("/planilla")
    public String pago(){
        return "planilla-pago";
    }

    @GetMapping("/obtener-planilla/{id}")
    public String obtenerPlanilla(@PathVariable("id") String id){
        PlanillaEntity planilla = pagoService.generarPlanillaPago(id);
        Model model = null;
        model.addAttribute("planilla",planilla);
        return "redirect:/planilla";
    }
}
