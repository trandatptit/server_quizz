package com.trandat.btl_ltw.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KetQuaResponse {
    Long ketQuaId;
    float diem;
    LocalDateTime ngaythi;
    String tenUser;
    String tenDeThi;
    int soCauDung;
    int tongSoCau;
    List<ChiTietKetQuaResponse> dsChiTietKetQua;
}
