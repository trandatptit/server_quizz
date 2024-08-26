package com.trandat.btl_ltw.service;

import com.trandat.btl_ltw.dto.request.MonThiRequest;
import com.trandat.btl_ltw.dto.response.DeThiResponse;
import com.trandat.btl_ltw.dto.response.MonThiDeTailResponse;
import com.trandat.btl_ltw.dto.response.MonThiResponse;
import com.trandat.btl_ltw.entity.DeThi;
import com.trandat.btl_ltw.entity.KyThi;
import com.trandat.btl_ltw.entity.MonThi;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.DeThiMapper;
import com.trandat.btl_ltw.mapper.MonThiMapper;
import com.trandat.btl_ltw.repository.MonThiRepository;
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
public class MonThiService {
    MonThiRepository monThiRepository;
    MonThiMapper monThiMapper;
    DeThiMapper deThiMapper;
    @PreAuthorize("hasRole('ADMIN')")
    public MonThiResponse creatMonThi(MonThiRequest request){
        log.info("in creatMonThi");
        if(monThiRepository.existsByTenMon(request.getTenMon()))
            throw new AppException(ErrorCode.MONTHI_EXISTED);
       MonThi monThi = monThiMapper.toMonThi(request);
        return monThiMapper.toMonThiResponse(monThiRepository.save(monThi));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MonThiResponse updateMonThi(Long monthiId, MonThiRequest request){

        MonThi monThi = monThiRepository.findById(monthiId)
                .orElseThrow(() -> new AppException(ErrorCode.MONTHI_NOT_EXISTED));
        monThiMapper.updateMonThi(monThi, request);
        return monThiMapper.toMonThiResponse(monThiRepository.save(monThi));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String  deleteMonThi(Long monthiId){

        MonThi monThi = monThiRepository.findById(monthiId)
                .orElseThrow(() -> new AppException(ErrorCode.MONTHI_NOT_EXISTED));
        String message = "";
        if(monThi.getDeThis().isEmpty()){

            monThiRepository.deleteById(monthiId);
            message = "Đã xóa môn thi có id = " + monthiId;
        }
        else {
            message = "Không thể xóa do môn thi đã chứa đề thi!";
        }
        return message;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<MonThiDeTailResponse> getAllMonThi(){
        log.info("in method getMonThi");
        List<MonThiDeTailResponse> monThiDeTailResponseList = new ArrayList<>();
        for(MonThi x : monThiRepository.findAll()){
            monThiDeTailResponseList.add(
                    MonThiDeTailResponse.builder()
                            .id(x.getId())
                            .tenMon(x.getTenMon())
                            .soLuongDeThi(x.getDeThis().size())
                            .build()
            );
        }
        return monThiDeTailResponseList.stream().toList();
    }

    @PreAuthorize("hasRole('USER')")
    public List<DeThiResponse> getDeThiInMonThi(Long monThiId){
        MonThi monThi = monThiRepository.findById(monThiId)
                .orElseThrow(() -> new AppException(ErrorCode.MONTHI_NOT_EXISTED));
        Set<DeThiResponse> deThiResponseSet = new HashSet<>();
        for(DeThi x : monThi.getDeThis()){
            deThiResponseSet.add(deThiMapper.toDeThiResponse(x));
        }
        List<DeThiResponse> dethis = new ArrayList<>(deThiResponseSet);
        return dethis;
    }
}
