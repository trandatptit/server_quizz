package com.trandat.btl_ltw.service;

import com.trandat.btl_ltw.dto.request.KyThiRequest;
import com.trandat.btl_ltw.dto.response.DeThiResponse;
import com.trandat.btl_ltw.dto.response.KyThiDetailResponse;
import com.trandat.btl_ltw.dto.response.KyThiResponse;
import com.trandat.btl_ltw.dto.response.MonThiDeTailResponse;
import com.trandat.btl_ltw.entity.DeThi;
import com.trandat.btl_ltw.entity.KyThi;
import com.trandat.btl_ltw.entity.MonThi;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.DeThiMapper;
import com.trandat.btl_ltw.mapper.KyThiMapper;
import com.trandat.btl_ltw.repository.KyThiRepository;
import com.trandat.btl_ltw.repository.UserRepository;
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
public class KyThiService {
    private final UserRepository userRepository;
    KyThiRepository kyThiRepository;
    KyThiMapper kyThiMapper;
    DeThiMapper deThiMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public KyThiResponse creatKyThi(KyThiRequest request){
        if(kyThiRepository.existsByTen(request.getTen()))
            throw new AppException(ErrorCode.KYTHI_EXISTED);
        KyThi kyThi = kyThiMapper.toKyThi(request);
        return kyThiMapper.toKyThiResponse(kyThiRepository.save(kyThi));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public KyThiResponse updateKyThi(Long kythiId, KyThiRequest request){

        KyThi kyThi = kyThiRepository.findById(kythiId)
                .orElseThrow(() -> new AppException(ErrorCode.KYTHI_NOT_EXISTED));
        kyThiMapper.updateKyThi(kyThi, request);
        return kyThiMapper.toKyThiResponse(kyThiRepository.save(kyThi));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String deleteKyThi(Long kyThiId){
        KyThi kyThi = kyThiRepository.findById(kyThiId)
                .orElseThrow(() -> new AppException(ErrorCode.KYTHI_NOT_EXISTED));
        String message = "";
        if(kyThi.getDeThis().isEmpty()){
            kyThiRepository.deleteById(kyThiId);
            message = "Đã xóa thành công kỳ thi có id = " + kyThiId;
        }
        else{
            message = "Không thể xóa do kỳ thi có chứa đề thi!";
        }
        return message;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<KyThiDetailResponse> getAllKyThi(){
        log.info("in method getAllKyThi");
        List<KyThiDetailResponse> kyThiDetailResponseList = new ArrayList<>();
        for(KyThi x : kyThiRepository.findAll()){
            int soLuongDeMo = 0;
            int soLuongDeDong = 0;
            for(DeThi a : x.getDeThis()){
                if(a.getTrangThai()){
                    soLuongDeMo++;
                }
                else {
                    soLuongDeDong++;
                }
            }
            kyThiDetailResponseList.add(
                    KyThiDetailResponse.builder()
                            .id(x.getId())
                            .ten(x.getTen())
                            .soLuongDeThi(x.getDeThis().size())
                            .soLuongDeMo(soLuongDeMo)
                            .soLuongDeDong(soLuongDeDong)
                            .build()
            );
        }
        return kyThiDetailResponseList.stream().toList();
    }

    @PreAuthorize("hasRole('USER')")
    public List<DeThiResponse> getDeThiInkyThi(Long kythiId){
        KyThi kyThi = kyThiRepository.findById(kythiId)
            .orElseThrow(() -> new AppException(ErrorCode.KYTHI_NOT_EXISTED));
        Set<DeThiResponse> deThiResponseSet = new HashSet<>();
        for(DeThi x : kyThi.getDeThis()){
            deThiResponseSet.add(deThiMapper.toDeThiResponse(x));
        }
        List<DeThiResponse> dethis = new ArrayList<>(deThiResponseSet);
        return dethis;
    }
}
