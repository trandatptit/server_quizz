package com.trandat.btl_ltw.mapper;

import com.trandat.btl_ltw.dto.response.KetQuaResponse;
import com.trandat.btl_ltw.entity.KetQua;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KetQuaMapper {
    @Mapping(target = "tenUser", source = "user.fullName")
    @Mapping(target = "tenDeThi", source = "deThi.ten")
    KetQuaResponse toKetQuaResponse(KetQua ketQua);
}
