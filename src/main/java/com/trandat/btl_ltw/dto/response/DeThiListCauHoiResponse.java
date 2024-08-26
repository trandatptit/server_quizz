package com.trandat.btl_ltw.dto.response;


import com.trandat.btl_ltw.entity.CauHoi;
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
public class DeThiListCauHoiResponse {
    Long id;
    String ten;
    Boolean trangThai;
    int thoiGianLamBai;
    LocalDateTime lichThi;
    List<CauHoiResponse> dsCauHoi;
}
