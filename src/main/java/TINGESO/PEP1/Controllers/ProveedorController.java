package TINGESO.PEP1.Controllers;

import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;
    @GetMapping("/listar-proveedores")
    public String listar(Model model){
        List<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores",proveedores);
        return "index-listar";
    }

    @GetMapping("/nuevo-proveedor")
    public String proveedor(){
        return "new-proveedor";
    }
    @PostMapping("/nuevo-proveedor")
    public String nuevoProveedor(@RequestParam("codigo") String codigo,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("categoria") String categoria,
                                 @RequestParam("retencion") Integer retencion){

        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCode(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        proveedorService.guardarProveedor(proveedor);
        return "redirect:/nuevo-proveedor";
    }


}
