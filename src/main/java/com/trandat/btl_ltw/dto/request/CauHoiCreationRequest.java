package com.trandat.btl_ltw.dto.request;

import com.trandat.btl_ltw.entity.MonThi;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CauHoiCreationRequest {
    String deBai;
    String a;
    String b;
    String c;
    String d;
    String dapAn;
}
