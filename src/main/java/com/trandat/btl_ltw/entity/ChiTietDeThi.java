//package com.trandat.btl_ltw.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Entity
//@Table(name = "ChiTietDeThi")
//public class ChiTietDeThi {
//    @Id
//    @SequenceGenerator(
//            name = "product_sequence",
//            sequenceName = "product_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "product_sequence"
//    )
//    Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "cauhoi_id", referencedColumnName = "cauhoi_id")
//    CauHoi cauHoi;
//
//    @ManyToOne
//    @JoinColumn(name = "dethi_id", referencedColumnName = "dethi_id")
//    DeThi deThi;
//
//}
