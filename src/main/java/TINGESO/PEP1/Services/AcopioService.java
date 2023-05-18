package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Repositories.AcopioRepository;
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
import java.util.ArrayList;

@Service
public class AcopioService {
    @Autowired
    AcopioRepository acopioRepository;
    private final Logger log = LoggerFactory.getLogger(AcopioService.class);
    public ArrayList<AcopioEntity> getAcopio(){
        ArrayList<AcopioEntity> acopio = new ArrayList<>();
        acopioRepository.findAll().forEach(acopio::add);
        return acopio;
    }
    @Generated
    public String guardar(MultipartFile file){
        String fileNames = file.getOriginalFilename();
        String mensaje = "";
        try{
            byte [] bytes = file.getBytes();
            Path path = Paths.get(fileNames);
            Files.write(path, bytes);
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
            String temp = "";
            String bfread;
            int count = 1;
            while((bfread = bf.readLine()) != null){
                if(count == 1){
                    count= 0;
                }else{
                    String[] datos = bfread.split(";");
                    guardarAcopioDB(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
                    temp = temp + "\n" + bfread;
                }
            }
            texto= temp;
            System.out.println("Achivo leido correctamente: " + texto);
        }catch (Exception e){
            log.error("Error al leer el archivo: " + e.getMessage());
        }finally{
            if(bf != null)
                try{
                    bf.close();
                }catch (Exception e){
                    log.error("Error al cerrar el archivo: " + e.getMessage());
                }
        }
    }

    public void guardarAcopioDB(String fecha, String turno, int id_proovedor, int kg_leche){
        AcopioEntity acopio = new AcopioEntity();
        acopio.setFecha(fecha);
        acopio.setTurno(turno);
        acopio.setId_proveedor(id_proovedor);
        acopio.setKg_leche(kg_leche);

        acopioRepository.save(acopio);
    }
}
