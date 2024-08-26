package com.trandat.btl_ltw.mapper;

import com.trandat.btl_ltw.dto.request.MonThiRequest;
import com.trandat.btl_ltw.dto.response.MonThiResponse;
import com.trandat.btl_ltw.entity.MonThi;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MonThiMapper {
    MonThi toMonThi(MonThiRequest request);
    MonThiResponse toMonThiResponse(MonThi monThi);
    void updateMonThi(@MappingTarget MonThi monThi, MonThiRequest request);
}
