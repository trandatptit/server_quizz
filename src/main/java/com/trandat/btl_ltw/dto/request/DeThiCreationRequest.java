package com.trandat.btl_ltw.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeThiCreationRequest {
    String ten;
    Boolean trangThai;
    int thoiGianLamBai;
    LocalDateTime lichThi;
}
