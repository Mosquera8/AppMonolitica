package TINGESO.PEP1.Services;

import TINGESO.PEP1.Entities.AcopioEntity;
import TINGESO.PEP1.Entities.ProveedorEntity;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            String temp = bf.readLine();
            String bfread;
            while((bfread = bf.readLine()) != null){

                String[] datos = bfread.split(";");
                guardarAcopioDB(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]));
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

    @Generated
    public void guardarAcopioDB(String fecha, String turno, String codigo, Double kg_leche){
        AcopioEntity acopio = new AcopioEntity();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        acopio.setFecha(LocalDate.parse(fecha, formatter));
        acopio.setTurno(turno);
        acopio.setProveedor(codigo);
        acopio.setKg_leche(kg_leche);

        acopioRepository.save(acopio);
    }

    public List<AcopioEntity> quincenaPorProveedor(String codigo, int tipo){
        List<AcopioEntity> quincena;
        if(tipo == 1){
            quincena = ultimaQuincena();
        }else{
            quincena = quincenaAnterior();
        }
        return quincena.stream().filter(AcopioEntity -> AcopioEntity.getProveedor().equals(codigo)).toList();
    }
    public List<AcopioEntity> ultimaQuincena() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate inicioQuincena = fechaActual.minusDays(14);
        return acopioRepository.findAcopiosBetweenDates(inicioQuincena, fechaActual);
    }

    public List<AcopioEntity> quincenaAnterior(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate finalQuincenaAnterior = fechaActual.minusDays(15);
        LocalDate inicioQuincenaAnterior = finalQuincenaAnterior.minusDays(14);
        return acopioRepository.findAcopiosBetweenDates(inicioQuincenaAnterior, finalQuincenaAnterior);
    }

}
