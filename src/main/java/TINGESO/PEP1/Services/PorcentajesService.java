package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Entities.PorcentajesEntity;
import TINGESO.PEP1.Entities.ProveedorEntity;
import TINGESO.PEP1.Repositories.PorcentajesRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PorcentajesService {

    @Autowired
    PorcentajesRepository porcentajesRepository;
    @Autowired
    ProveedorService proveedorService;


    private final Logger log = LoggerFactory.getLogger(AcopioService.class);

    public PorcentajesEntity obtenerPorCodigoYFecha(String codigo, LocalDate fecha){
        return porcentajesRepository.findByProveedorAndFecha(codigo, fecha);
    }
    public ArrayList<PorcentajesEntity> getPorcentajes(){
        ArrayList<PorcentajesEntity> porcentajes = new ArrayList<>();
        porcentajesRepository.findAll().forEach(porcentajes::add);
        return porcentajes;
    }
    @Generated
    public String guardar(MultipartFile file){
        String fileNames = file.getOriginalFilename();
        String mensaje = "";
        try{
            byte [] bytes = file.getBytes();
            Path path = Paths.get(fileNames);
            Files.write(path, bytes);
            ArrayList<PorcentajesEntity> porcentajes = new ArrayList<>();
            porcentajesRepository.saveAll(porcentajes);
            mensaje = "Archivo guardado correctamente: " + file.getOriginalFilename();
            log.info(mensaje);
        }catch (Exception e){
            mensaje = "Error al subir el archivo: " + file.getOriginalFilename();
            log.error(mensaje, e.getMessage());
        }
        return mensaje;
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        //acopioReposirtory.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = bf.readLine();
            String bfread;
            while((bfread = bf.readLine()) != null){
                texto = temp;
                String[] datos = bfread.split(";");
                guardarPorcentajesDB(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]));
                temp = temp + "\n" + bfread;
            }
            texto = temp;
            System.out.println("Archivo leido correctamente: " + texto);
        }catch (Exception e){
            log.error("Error al leer el archivo: " + e.getMessage());
        }finally {
            if(bf != null)
                try{
                    bf.close();
                }catch (Exception e){
                    log.error("Error al cerrar el archivo: " + e.getMessage());
                }
        }
    }

    @Generated
    public void guardarPorcentajesDB(String codigo,int grasas,int solidos){
        PorcentajesEntity porcentajesEntity = new PorcentajesEntity();
        porcentajesEntity.setProveedor(codigo);
        porcentajesEntity.setGrasas(grasas);
        porcentajesEntity.setSolidos(solidos);
        porcentajesEntity.setFecha(LocalDate.now());
        porcentajesRepository.save(porcentajesEntity);
    }
}
