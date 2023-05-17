package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Repositories.AcopioReposirtory;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private AcopioReposirtory acopioReposirtory;
    private final Logger log = LoggerFactory.getLogger(AcopioService.class);
    public ArrayList<AcopioEntity> getAcopio(){
        ArrayList<AcopioEntity> acopio = new ArrayList<>();
        acopioReposirtory.findAll().forEach(acopio::add);
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
            ArrayList<AcopioEntity> acopio = new ArrayList<>();
            acopioReposirtory.saveAll(acopio);
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
                String[] datos = texto.split(",");
                guardarAcopioDB(datos[0], datos[1].charAt(0), Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
                temp = temp + "\n" + bfread;
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

    public void guardarAcopioDB(String fecha, char turno, int id_proovedor, int kg_leche){
        AcopioEntity acopio = new AcopioEntity();
        acopio.setFecha(fecha);
        acopio.setTurno(turno);
        acopio.setId_proveedor(id_proovedor);
        acopio.setKg_leche(kg_leche);

        acopioReposirtory.save(acopio);
    }
}
