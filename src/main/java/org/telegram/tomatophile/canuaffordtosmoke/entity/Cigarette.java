package org.telegram.tomatophile.canuaffordtosmoke.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cigarettes")
@Accessors(chain = true)
public class Cigarette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "price")
    private float price;
}
