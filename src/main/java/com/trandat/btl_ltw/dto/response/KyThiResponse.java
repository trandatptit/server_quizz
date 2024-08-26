package com.trandat.btl_ltw.dto.response;


import com.trandat.btl_ltw.entity.DeThi;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KyThiResponse {
    Long id;
    String ten;
}
