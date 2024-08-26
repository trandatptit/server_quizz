package com.trandat.btl_ltw.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonThiDeTailResponse {
    Long id;
    String tenMon;
    int soLuongDeThi;
//    Set<CauHoi> dsCauHoi;
}
