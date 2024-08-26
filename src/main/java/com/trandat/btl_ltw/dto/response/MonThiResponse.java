package com.trandat.btl_ltw.dto.response;

import com.trandat.btl_ltw.entity.CauHoi;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonThiResponse {
    Long id;
    String tenMon;
//    Set<CauHoi> dsCauHoi;
}
