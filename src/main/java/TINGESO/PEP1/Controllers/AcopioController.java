package TINGESO.PEP1.Controllers;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Services.AcopioService;

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
public class AcopioController {
    @Autowired
    AcopioService acopioService;

    @GetMapping("/acopio")
    public String acopio(){
        return "acopio";
    }
    @PostMapping("/acopio")
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes attributes){
        acopioService.guardar(file);
        attributes.addFlashAttribute("message","Archivo cargado correctamente");
        acopioService.leerCsv("Acopio.csv");
        return "redirect:/acopio";
    }
    @GetMapping("/fileInformationAcopio")
    public String listar(Model model){
        ArrayList<AcopioEntity> datas = acopioService.getAcopio();
        model.addAttribute("datas",datas);
        return "fileInformationAcopio";
    }
}
