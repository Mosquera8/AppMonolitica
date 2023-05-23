package TINGESO.PEP1.Controllers;


import TINGESO.PEP1.Entities.PorcentajesEntity;
import TINGESO.PEP1.Services.PorcentajesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PorcentajesController {
    @Autowired
    PorcentajesService porcentajesService;

    @GetMapping("/porcentajes")
    public String porcentajes(){
        return "porcentajes";
    }
    @PostMapping("/porcentajes")
    public String subirPorcentajes(@RequestParam("file") MultipartFile file, RedirectAttributes atributos){
        porcentajesService.guardar(file);
        atributos.addFlashAttribute("message","Archivo cargado correctamente");
        porcentajesService.leerCsv("Porcentajes.csv");
        return "redirect:/porcentajes";
    }
    @GetMapping("/fileInformationPorcentaje")
    public String listar(Model model){
        ArrayList<PorcentajesEntity> datas = porcentajesService.getPorcentajes();
        model.addAttribute("datas",datas);
        return "fileInformationPorcentajes";
    }

}
