package com.trandat.btl_ltw.dto.response;

import com.trandat.btl_ltw.entity.KetQua;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeThiThongKeResponse {
     String tenDeThi;
     String tenKyThi;
     String tenMonThi;
     Boolean trangThai;
     int soNguoiThamGia;
     float diemTrungBinh;

}
