package com.trandat.btl_ltw.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeThiDetailResponse {
    Long id;
    String ten;
    Boolean trangThai;
    int thoiGianLamBai;
    String lichThi;
    String tenMonThi;
    String tenKyThi;
//    int soKetQua;
    int soCauHoi;
}
