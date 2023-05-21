package TINGESO.PEP1.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "porcentajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorcentajesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    public Integer ID;

    public String proveedor;
    public Integer grasas;
    public Integer solidos;
    public LocalDate fecha;

}
