package com.trandat.btl_ltw.dto.request;

import com.trandat.btl_ltw.dto.response.CauHoiResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeThiUpdateRequest {
    String ten;
    Boolean trangThai;
    int thoiGianLamBai;
    LocalDateTime lichThi;
    Set<CauHoiResponse> dsCauHoi;
}
