package com.trandat.btl_ltw.mapper;


import com.trandat.btl_ltw.dto.request.KyThiRequest;
import com.trandat.btl_ltw.dto.response.KyThiResponse;
import com.trandat.btl_ltw.entity.KyThi;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface KyThiMapper {
    KyThi toKyThi(KyThiRequest request);
    KyThiResponse toKyThiResponse(KyThi kyThi);
    void updateKyThi(@MappingTarget KyThi kyThi, KyThiRequest request);
}
