package com.trandat.btl_ltw.service;

import com.trandat.btl_ltw.dto.request.CauHoiCreationRequest;
import com.trandat.btl_ltw.dto.request.DeThiCreationInputRequest;
import com.trandat.btl_ltw.dto.request.DeThiCreationRequest;
import com.trandat.btl_ltw.dto.request.DeThiUpdateRequest;
import com.trandat.btl_ltw.dto.response.*;
import com.trandat.btl_ltw.entity.*;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.CauHoiMapper;
import com.trandat.btl_ltw.mapper.DeThiMapper;
import com.trandat.btl_ltw.mapper.KetQuaMapper;
import com.trandat.btl_ltw.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeThiService {
    DeThiRepository deThiRepository;
    DeThiMapper deThiMapper;
    KyThiRepository kyThiRepository;
    MonThiRepository monThiRepository;
    CauHoiRepository cauHoiRepository;
    CauHoiMapper cauHoiMapper;
    KetQuaRepository ketQuaRepository;
    KetQuaMapper ketQuaMapper;


    @PreAuthorize("hasRole('ADMIN')")
    public DeThiResponse creatDeThi(Long kyThiId, Long monThiId, DeThiCreationInputRequest request){
        if(deThiRepository.existsDeThiByTen(request.getTen())){
            throw new AppException(ErrorCode.DETHI_EXISTED);
        }
        KyThi kyThi = kyThiRepository.findById(kyThiId)
                .orElseThrow(() -> new AppException(ErrorCode.KYTHI_NOT_EXISTED));
        MonThi monThi = monThiRepository.findById(monThiId)
                .orElseThrow(() -> new AppException(ErrorCode.MONTHI_NOT_EXISTED));
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getLichThi(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        DeThiCreationRequest deThiCreationRequest = DeThiCreationRequest.builder()
                .ten(request.getTen())
                .trangThai(request.getTrangThai())
                .thoiGianLamBai(request.getThoiGianLamBai())
                .lichThi(localDateTime)
                .build();
        DeThi deThi = deThiMapper.toDeThi(deThiCreationRequest);
        deThi.setKyThi(kyThi);
        deThi.setMonThi(monThi);
        return deThiMapper.toDeThiResponse(deThiRepository.save(deThi));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DeThiResponse updateDeThi(Long deThiId, DeThiCreationInputRequest request){
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(request.getLichThi(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        DeThiCreationRequest deThiCreationRequest = DeThiCreationRequest.builder()
                .ten(request.getTen())
                .trangThai(request.getTrangThai())
                .thoiGianLamBai(request.getThoiGianLamBai())
                .lichThi(localDateTime)
                .build();
        deThiMapper.updateDeThi(deThi, deThiCreationRequest);
        return deThiMapper.toDeThiResponse(deThiRepository.save(deThi));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public DeThiListCauHoiResponse getListCauHoi(Long deThiId){
        log.info("in method getListCauHoi");
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        DeThiListCauHoiResponse deThiListCauHoiResponse = new DeThiListCauHoiResponse();
        Set<CauHoiResponse> dsCauHoiResponses = new HashSet<>();
        for(CauHoi x : deThi.getDsCauHoi()){
            dsCauHoiResponses.add(cauHoiMapper.toCauHoiResponse(x));
        }
        List<CauHoiResponse> dsCauHoiResponsesList = new ArrayList<>(dsCauHoiResponses);
        Collections.sort(dsCauHoiResponsesList, new Comparator<CauHoiResponse>() {
            @Override
            public int compare(CauHoiResponse cauHoi1, CauHoiResponse cauHoi2) {
                return Integer.compare(Math.toIntExact(cauHoi1.getId()), Math.toIntExact(cauHoi2.getId()));
            }
        });
        return DeThiListCauHoiResponse.builder()
                .id(deThiId)
                .ten(deThi.getTen())
                .trangThai(deThi.getTrangThai())
                .thoiGianLamBai(deThi.getThoiGianLamBai())
                .lichThi(deThi.getLichThi())
                .dsCauHoi(dsCauHoiResponsesList)
                .build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    public DeThiListCauHoiResponse updateCauHoiDeThi(Long deThiId, DeThiUpdateRequest request){
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        Set<CauHoi> dsCauHoiDeThi = new HashSet<>();
        for(CauHoiResponse x : request.getDsCauHoi()){
            CauHoiCreationRequest cauHoiCreationRequest = CauHoiCreationRequest.builder()
                        .deBai(x.getDeBai())
                        .a(x.getA())
                        .b(x.getB())
                        .c(x.getC())
                        .d(x.getD())
                        .dapAn(x.getDapAn())
                        .build();
            if(x.getId()!=null){
                Long cauHoiId = x.getId();
                CauHoi cauHoi = cauHoiRepository.findById(cauHoiId)
                        .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED));
                cauHoiMapper.updateCauHoi(cauHoi, cauHoiCreationRequest);
                dsCauHoiDeThi.add(cauHoi);
                cauHoiRepository.save(cauHoi);
            } else{
                CauHoi cauHoi = cauHoiMapper.toCauHoi(cauHoiCreationRequest);
                cauHoi.setMonThi(deThi.getMonThi());
                dsCauHoiDeThi.add(cauHoi);
                cauHoiRepository.save(cauHoi);
            }
        }
        Set<CauHoiResponse> dsCauHoiResponse = new HashSet<>();
        for(CauHoi x : dsCauHoiDeThi){
            dsCauHoiResponse.add(cauHoiMapper.toCauHoiResponse(x));
        }
        deThi.builder()
                .ten(request.getTen())
                .trangThai(request.getTrangThai())
                .thoiGianLamBai(request.getThoiGianLamBai())
                .lichThi(request.getLichThi())
                .dsCauHoi(dsCauHoiDeThi)
                .build();
        deThiRepository.save(deThi);
        List<CauHoiResponse> dsCauHoiResponsesList = new ArrayList<>(dsCauHoiResponse);
        return DeThiListCauHoiResponse.builder()
                .id(deThiId)
                .ten(deThi.getTen())
                .trangThai(deThi.getTrangThai())
                .thoiGianLamBai(deThi.getThoiGianLamBai())
                .lichThi(deThi.getLichThi())
                .dsCauHoi(dsCauHoiResponsesList)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public  List<DeThiDetailResponse> getALLDeThi(){
        List<DeThiDetailResponse> deThiDetailResponseList = new ArrayList<>();
        for(DeThi x : deThiRepository.findAll()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String formattedLichThi = x.getLichThi().format(formatter);
            deThiDetailResponseList.add(
                    DeThiDetailResponse.builder()
                            .id(x.getId())
                            .ten(x.getTen())
                            .trangThai(x.getTrangThai())
                            .thoiGianLamBai(x.getThoiGianLamBai())
                            .lichThi(formattedLichThi)
                            .tenMonThi(x.getMonThi().getTenMon())
                            .tenKyThi(x.getKyThi().getTen())
                            .soCauHoi(x.getDsCauHoi().size())
                            .build()
            );
        }
        return deThiDetailResponseList;
    }

    @PreAuthorize("hasRole('USER')")
    public List<DeThiDetailResponse> getALLDeThiUSER(){
        List<DeThiDetailResponse> deThiDetailResponseList = new ArrayList<>();
        for(DeThi x : deThiRepository.findAll()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String formattedLichThi = x.getLichThi().format(formatter);
            deThiDetailResponseList.add(
                    DeThiDetailResponse.builder()
                            .id(x.getId())
                            .ten(x.getTen())
                            .trangThai(x.getTrangThai())
                            .thoiGianLamBai(x.getThoiGianLamBai())
                            .lichThi(formattedLichThi)
                            .tenMonThi(x.getMonThi().getTenMon())
                            .tenKyThi(x.getKyThi().getTen())
                            .soCauHoi(x.getDsCauHoi().size())
                            .build()
            );
        }
        return deThiDetailResponseList;
    }

    @PreAuthorize("hasRole('USER')")
    public List<DeThiResponse> getDeThiStatus(Boolean status){
        List<DeThi> deThis = deThiRepository.findDeThisByTrangThai(status)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        List<DeThiResponse> deThiResponseLis= new ArrayList<>();
        for(DeThi x : deThis){
            deThiResponseLis.add(deThiMapper.toDeThiResponse(x));
        }
        return deThiResponseLis;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDeThi(Long deThiId){
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        String message = "";
        if(deThi.getKetQuas().isEmpty()){
            deThi.setMonThi(null);
            deThi.setKyThi(null);
            for (CauHoi x : deThi.getDsCauHoi()){
                if(x.getDsDeThi().size()==1){
                    x.setMonThi(null);
                    x.getDsDeThi().remove(deThi);
                    cauHoiRepository.save(x);
                    cauHoiRepository.deleteById(x.getId());
                }
            }
            deThiRepository.save(deThi);
            deThiRepository.deleteById(deThiId);
            message = "Đã xóa Đề Thi có id = " + deThiId;
        }
        else{
            message = "Không thể xóa do đề thi đã có sinh viên nộp bài!";
        }
        return message;
    }
    @Transactional
    public void openExamsIfNecessary() {
        LocalDateTime now = LocalDateTime.now();
        List<DeThi> examsToOpen = deThiRepository.findDeThisByTrangThaiFalseAndLichThiBefore(now)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));// Tìm các bài thi cần mở
        for (DeThi exam : examsToOpen) {
            exam.setTrangThai(Boolean.TRUE);
            deThiRepository.save(exam);

        }
    }
}
