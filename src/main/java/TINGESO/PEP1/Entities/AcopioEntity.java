package TINGESO.PEP1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "acopio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer ID;
    public LocalDate fecha;
    public String turno;
    public String proveedor;
    public Double kg_leche;



}
