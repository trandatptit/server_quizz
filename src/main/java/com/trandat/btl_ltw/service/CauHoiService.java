package com.trandat.btl_ltw.service;


import com.trandat.btl_ltw.dto.request.CauHoiCreationRequest;
import com.trandat.btl_ltw.dto.response.CauHoiResponse;
import com.trandat.btl_ltw.entity.CauHoi;
import com.trandat.btl_ltw.entity.DeThi;
import com.trandat.btl_ltw.entity.MonThi;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.CauHoiMapper;
import com.trandat.btl_ltw.mapper.MonThiMapper;
import com.trandat.btl_ltw.repository.CauHoiRepository;
import com.trandat.btl_ltw.repository.DeThiRepository;
import com.trandat.btl_ltw.repository.MonThiRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CauHoiService {
    CauHoiRepository cauHoiRepository;
    CauHoiMapper cauHoiMapper;
    MonThiRepository monThiRepository;
    MonThiMapper monThiMapper;
    DeThiRepository deThiRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<CauHoiResponse> creatCauHoi(Long deThiId, List<CauHoiCreationRequest> request){
        List<CauHoiResponse> dsCauHoi = new ArrayList<>();
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        MonThi monThi = monThiRepository.findById(deThi.getMonThi().getId())
                .orElseThrow(() -> new AppException(ErrorCode.MONTHI_NOT_EXISTED));
        Set<CauHoi> cauHoiSet = deThi.getDsCauHoi();
        if(cauHoiSet.isEmpty()){
            cauHoiSet = new HashSet<>();
        }
        for(CauHoiCreationRequest x : request){

            CauHoi cauHoi = cauHoiMapper.toCauHoi(x);
            cauHoi.setMonThi(monThi);
            cauHoiSet.add(cauHoi);
            CauHoi saveCauHoi = cauHoiRepository.save(cauHoi);
            dsCauHoi.add(cauHoiMapper.toCauHoiResponse(saveCauHoi));
        }
        deThi.setDsCauHoi(cauHoiSet);
        deThiRepository.save(deThi);
        return dsCauHoi;
    }


    @PreAuthorize("hasRole('ADMIN')")
    public CauHoiResponse updateCauHoi(Long cauHoiId, CauHoiCreationRequest request){
        CauHoi cauHoi = cauHoiRepository.findById(cauHoiId)
                .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED));
        cauHoiMapper.updateCauHoi(cauHoi, request);
        return cauHoiMapper.toCauHoiResponse(cauHoiRepository.save(cauHoi));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCauHoi(Long cauHoiId){
        CauHoi cauHoi = cauHoiRepository.findById(cauHoiId)
                .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED));
        cauHoi.setMonThi(null);
        cauHoi.setDsDeThi(null);
        cauHoiRepository.deleteById(cauHoiId);
    }

}
