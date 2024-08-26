package com.trandat.btl_ltw.mapper;

import com.trandat.btl_ltw.dto.request.CauTraLoiRequest;
import com.trandat.btl_ltw.entity.ChiTietKetQua;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChiTietKetQuaMapper {


    ChiTietKetQua toChiTietKetQua(CauTraLoiRequest cauTraLoiRequest);

}
