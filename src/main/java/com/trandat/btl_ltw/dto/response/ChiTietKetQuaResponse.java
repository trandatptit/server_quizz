package com.trandat.btl_ltw.dto.response;

import com.trandat.btl_ltw.entity.CauHoi;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChiTietKetQuaResponse {
    CauHoiResponse cauHoi;
    String cauTraLoi;
}
