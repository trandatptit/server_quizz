package com.trandat.btl_ltw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class KyThi {
    @Id
//    @SequenceGenerator(
//            name = "product_sequence",
//            sequenceName = "product_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "product_sequence"
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kythi_id")
    Long id;
    String ten;


    @OneToMany(mappedBy = "kyThi", fetch = FetchType.LAZY)
    Set<DeThi> deThis = new HashSet<>();

}
