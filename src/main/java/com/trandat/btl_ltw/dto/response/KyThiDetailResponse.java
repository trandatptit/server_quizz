package com.trandat.btl_ltw.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KyThiDetailResponse {
    Long id;
    String ten;
    int soLuongDeThi;
    int soLuongDeMo;
    int soLuongDeDong;
}
