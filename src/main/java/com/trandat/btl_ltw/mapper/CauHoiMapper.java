package com.trandat.btl_ltw.mapper;

import com.trandat.btl_ltw.dto.request.CauHoiCreationRequest;
import com.trandat.btl_ltw.dto.response.CauHoiResponse;
import com.trandat.btl_ltw.entity.CauHoi;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CauHoiMapper {
    CauHoi toCauHoi(CauHoiCreationRequest request);
    CauHoiResponse toCauHoiResponse(CauHoi cauHoi);
    void updateCauHoi(@MappingTarget CauHoi cauHoi, CauHoiCreationRequest request);
}
