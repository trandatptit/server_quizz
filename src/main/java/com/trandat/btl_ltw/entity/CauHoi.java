package com.trandat.btl_ltw.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class CauHoi {

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
    @Column(name = "cauhoi_id")
    Long id;
    String deBai;
    String a;
    String b;
    String c;
    String d;
    String dapAn;

    @ManyToOne
    @JoinColumn(name = "monthi_id")
    MonThi monThi;

//    @OneToMany(mappedBy = "cauHoi", fetch = FetchType.EAGER)
//    List<ChiTietDeThi> chiTietDeThis = new ArrayList<>();

    @ManyToMany(mappedBy = "dsCauHoi")
    Set<DeThi> dsDeThi;
//    @ManyToMany(mappedBy = "dsCauHoi", fetch = FetchType.EAGER)
//    Set<DeThi> dsDeThi = new HashSet<>();

}
