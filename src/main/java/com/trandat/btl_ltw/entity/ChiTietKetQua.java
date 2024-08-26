package com.trandat.btl_ltw.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class ChiTietKetQua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chitietketqua_id")
    Long id;
    Long cauHoiId;
    String cauTraLoi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ketqua_id", referencedColumnName = "ketqua_id")
    @JsonBackReference
    KetQua ketQua;

}
