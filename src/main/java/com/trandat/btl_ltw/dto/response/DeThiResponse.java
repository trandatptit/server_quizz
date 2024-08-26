package com.trandat.btl_ltw.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeThiResponse {
    Long id;
    String ten;
    Boolean trangThai;
    int thoiGianLamBai;
    LocalDateTime lichThi;
}
