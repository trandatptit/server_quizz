package com.trandat.btl_ltw.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserThongKeResponse {
    String tenUser;
    float diemTrungBinh;
    List<DeThiInUserResponse> dsDeThiDaLam;
}
