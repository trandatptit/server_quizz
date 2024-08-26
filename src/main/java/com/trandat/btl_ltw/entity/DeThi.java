package com.trandat.btl_ltw.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class DeThi {

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
    @Column(name = "dethi_id")
    Long id;
    String ten;
    Boolean trangThai;
    int thoiGianLamBai;
    LocalDateTime lichThi;

    @ManyToOne
    @JoinColumn(name = "monthi_id")
    MonThi monThi;

    @ManyToOne
    @JoinColumn(name = "kythi_id")
    KyThi kyThi;

//    @OneToMany(mappedBy = "deThi", fetch = FetchType.EAGER)
//    List<ChiTietDeThi> chiTietDeThis = new ArrayList<>();

    @OneToMany(mappedBy = "deThi", fetch = FetchType.EAGER)
    @JsonManagedReference
    Set<KetQua> ketQuas = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CauHoi_DeThi",
            joinColumns = @JoinColumn(name = "dethi_id"),
            inverseJoinColumns = @JoinColumn(name = "cauhoi_id")
    )
    Set<CauHoi> dsCauHoi;

//    @ManyToMany(mappedBy = "dsDeThiUser")
//    @JsonManagedReference
//    Set<User> danhsachUser;

}
