package com.trandat.btl_ltw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class MonThi {

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
    @Column(name ="monthi_id")
    Long id;
    String tenMon;

    @OneToMany(mappedBy = "monThi" , fetch = FetchType.EAGER)
    Set<CauHoi> dsCauHoi = new HashSet<>();

    @OneToMany(mappedBy = "monThi", fetch = FetchType.LAZY)
    Set<DeThi> deThis = new HashSet<>();

}
