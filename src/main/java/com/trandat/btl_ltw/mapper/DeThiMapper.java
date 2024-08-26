package com.trandat.btl_ltw.mapper;

import com.trandat.btl_ltw.dto.request.DeThiCreationRequest;
import com.trandat.btl_ltw.dto.response.DeThiListCauHoiResponse;
import com.trandat.btl_ltw.dto.response.DeThiResponse;
import com.trandat.btl_ltw.entity.DeThi;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeThiMapper {
    DeThi toDeThi(DeThiCreationRequest request);
    DeThiResponse toDeThiResponse(DeThi deThi);
    DeThiListCauHoiResponse toDeThiListCauHoiResponse(DeThi deThi);
    void updateDeThi(@MappingTarget DeThi deThi, DeThiCreationRequest request);
}
