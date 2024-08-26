package com.trandat.btl_ltw.service;

import com.trandat.btl_ltw.dto.request.CauTraLoiRequest;
import com.trandat.btl_ltw.dto.response.ChiTietKetQuaResponse;
import com.trandat.btl_ltw.dto.response.KetQuaResponse;
import com.trandat.btl_ltw.entity.*;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.CauHoiMapper;
import com.trandat.btl_ltw.mapper.ChiTietKetQuaMapper;
import com.trandat.btl_ltw.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KetQuaService {
    KetQuaRepository ketQuaRepository;
    DeThiRepository deThiRepository;
    UserRepository userRepository;
    CauHoiRepository cauHoiRepository;
    CauHoiMapper cauHoiMapper;
    ChiTietKetQuaRepository chiTietKetQuaRepository;
    ChiTietKetQuaMapper chiTietKetQuaMapper;

    @PreAuthorize("hasRole('USER')")
    public KetQuaResponse ketQua(Long deThiId, List<CauTraLoiRequest> request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        DeThi deThi = deThiRepository.findById(deThiId)
                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
        float tongDiem = 0f;
        int soCauDung = 0;
        int tongSoCau = deThi.getDsCauHoi().size();
        Set<ChiTietKetQua> dsChiTietKetQua = new HashSet<>();
        Set<ChiTietKetQuaResponse> dsChiTietKetQuaResponses = new HashSet<>();
        for(CauTraLoiRequest x : request){
            CauHoi cauHoi = cauHoiRepository.findById(x.getCauHoiId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED));
            ChiTietKetQua chiTietKetQua = chiTietKetQuaMapper.toChiTietKetQua(x);
//            dsChiTietKetQua.add(chiTietKetQua);
            if(x.getCauTraLoi().equals(cauHoi.getDapAn())){
                soCauDung++;
                tongDiem += (float)(10)/tongSoCau;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        tongDiem = Float.parseFloat(decimalFormat.format(tongDiem));
        LocalDateTime ngayThi = LocalDateTime.now();
        KetQua ketQua = KetQua.builder()
                .diem(tongDiem)
                .ngaythi(ngayThi)
                .deThi(deThi)
                .user(user)
                .chiTietKetQuas(dsChiTietKetQua)
                .build();
        ketQuaRepository.save(ketQua);
        for(CauTraLoiRequest x : request){
            CauHoi cauHoi = cauHoiRepository.findById(x.getCauHoiId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED));
            ChiTietKetQua chiTietKetQua = chiTietKetQuaMapper.toChiTietKetQua(x);
            chiTietKetQua.setKetQua(ketQua);
            dsChiTietKetQuaResponses.add(ChiTietKetQuaResponse.builder()
                    .cauTraLoi(chiTietKetQua.getCauTraLoi())
                    .cauHoi(cauHoiMapper.toCauHoiResponse(cauHoiRepository.findById(chiTietKetQua.getCauHoiId())
                            .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED))))

                    .build());
            chiTietKetQuaRepository.save(chiTietKetQua);
        }
        // Thêm KetQua vào DeThi
        deThi.getKetQuas().add(ketQua);
        deThiRepository.save(deThi);

        // Thêm KetQua vào User
        user.getKetQuas().add(ketQua);
        userRepository.save(user);

        List<ChiTietKetQuaResponse> dsChiTietKetQuaResponseList = new ArrayList<>(dsChiTietKetQuaResponses);
        Collections.sort(dsChiTietKetQuaResponseList, new Comparator<ChiTietKetQuaResponse>() {
            @Override
            public int compare(ChiTietKetQuaResponse ChiTietCauHoi1, ChiTietKetQuaResponse ChiTietCauHoi2) {
                return Integer.compare(Math.toIntExact(ChiTietCauHoi1.getCauHoi().getId()), Math.toIntExact(ChiTietCauHoi2.getCauHoi().getId()));
            }
        });

        return KetQuaResponse.builder()
                .diem(tongDiem)
                .ngaythi(ngayThi)
                .tenUser(user.getFullName())
                .tenDeThi(deThi.getKyThi().getTen()
                        + " " + deThi.getMonThi().getTenMon()
                        + " " + deThi.getTen())
                .soCauDung(soCauDung)
                .tongSoCau(tongSoCau)
                .dsChiTietKetQua(dsChiTietKetQuaResponseList)
                .build();
    }
}
