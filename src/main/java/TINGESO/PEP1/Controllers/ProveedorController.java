package TINGESO.PEP1.Controllers;

import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;
    @GetMapping("/listar")
    public String listar(Model model){
        List<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores",proveedores);
        return "index_listar";
    }

}
