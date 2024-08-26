package com.trandat.btl_ltw.service;

import com.trandat.btl_ltw.dto.response.*;
import com.trandat.btl_ltw.entity.ChiTietKetQua;
import com.trandat.btl_ltw.entity.DeThi;
import com.trandat.btl_ltw.entity.KetQua;
import com.trandat.btl_ltw.entity.User;
import com.trandat.btl_ltw.exception.AppException;
import com.trandat.btl_ltw.exception.ErrorCode;
import com.trandat.btl_ltw.mapper.CauHoiMapper;
import com.trandat.btl_ltw.mapper.KetQuaMapper;
import com.trandat.btl_ltw.mapper.UserMapper;
import com.trandat.btl_ltw.repository.CauHoiRepository;
import com.trandat.btl_ltw.repository.DeThiRepository;
import com.trandat.btl_ltw.repository.KetQuaRepository;
import com.trandat.btl_ltw.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongKeService {
    private final CauHoiRepository cauHoiRepository;

    UserRepository userRepository;
    UserMapper userMapper;

    DeThiRepository deThiRepository;
    KetQuaRepository ketQuaRepository;
    KetQuaMapper ketQuaMapper;
    CauHoiMapper cauHoiMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public UserThongKeResponse thongKeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        float diemTrungBinh = 0;
        Set<Long> dsIdDeThiDaLam = new HashSet<>();
        for (KetQua x : user.getKetQuas()) {
            dsIdDeThiDaLam.add(x.getDeThi().getId());
            diemTrungBinh += x.getDiem();
        }
        diemTrungBinh = diemTrungBinh / (user.getKetQuas().size());
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        diemTrungBinh = Float.parseFloat(decimalFormat.format(diemTrungBinh));
        Set<KetQua> dsKetQuaUser = user.getKetQuas();
        Set<DeThiInUserResponse> dsDeThiDaLam = new HashSet<>();
        for (Long deThiId : dsIdDeThiDaLam) {
            DeThi deThi = deThiRepository.findById(deThiId)
                    .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));

            Set<KetQua> dsKetQuaDaNop = user.getKetQuas().stream()
                    .filter(ketQua -> ketQua.getDeThi().getId().equals(deThiId))
                    .collect(Collectors.toSet());
            Set<KetQuaResponse> dsKetQuaDaNopResponses = new HashSet<>();
            for (KetQua x : dsKetQuaDaNop) {
                Set<ChiTietKetQuaResponse> dsChiTietKetQuaResponses = new HashSet<>();
                for (ChiTietKetQua chiTietKetQua : x.getChiTietKetQuas()) {
                    dsChiTietKetQuaResponses.add(
                            ChiTietKetQuaResponse.builder()
                                    .cauHoi(cauHoiMapper.toCauHoiResponse(cauHoiRepository
                                            .findById(chiTietKetQua.getCauHoiId())
                                            .orElseThrow(() -> new AppException(ErrorCode.CAUHOI_NOT_EXISTED))))
                                    .cauTraLoi(chiTietKetQua.getCauTraLoi())
                                    .build()
                    );
                }
                List<ChiTietKetQuaResponse> dsChiTietKetQuaResponseList = new ArrayList<>(dsChiTietKetQuaResponses);
                Collections.sort(dsChiTietKetQuaResponseList, new Comparator<ChiTietKetQuaResponse>() {
                    @Override
                    public int compare(ChiTietKetQuaResponse ChiTietCauHoi1, ChiTietKetQuaResponse ChiTietCauHoi2) {
                        return Integer.compare(Math.toIntExact(ChiTietCauHoi1.getCauHoi().getId()), Math.toIntExact(ChiTietCauHoi2.getCauHoi().getId()));
                    }
                });
                dsKetQuaDaNopResponses.add(
                        KetQuaResponse.builder()
                                .diem(x.getDiem())
                                .ngaythi(x.getNgaythi())
                                .tenUser(x.getUser().getFullName())
                                .tenDeThi(x.getDeThi().getTen())
                                .dsChiTietKetQua(dsChiTietKetQuaResponseList)
                                .build()
                );
            }
            List<KetQuaResponse> dsKetQuaResponseList = new ArrayList<>(dsKetQuaDaNopResponses);
            Collections.sort(dsKetQuaResponseList, new Comparator<KetQuaResponse>() {
                @Override
                public int compare(KetQuaResponse ketQua1, KetQuaResponse ketQua2) {
                    LocalDateTime ngayThi1 = ketQua1.getNgaythi();
                    LocalDateTime ngayThi2 = ketQua2.getNgaythi();
                    return ngayThi1.compareTo(ngayThi2);
                }
            });
            dsDeThiDaLam.add(DeThiInUserResponse.builder()
                    .id(deThi.getId())
                    .tenDeThi(deThi.getKyThi().getTen() + " - " + deThi.getMonThi().getTenMon() + " - " + deThi.getTen())
                    .soLanNopBai(dsKetQuaDaNop.size())
                    .dsKetQuaDaNop(dsKetQuaResponseList)
                    .build());
        }
        List<DeThiInUserResponse> deThiInUserResponseArrayList = new ArrayList<>(dsDeThiDaLam);
        Collections.sort(deThiInUserResponseArrayList, new Comparator<DeThiInUserResponse>() {
            @Override
            public int compare(DeThiInUserResponse o1, DeThiInUserResponse o2) {
                return Long.compare(o1.getId(), o2.getId());
            }
        });
        return UserThongKeResponse.builder()
                .tenUser(user.getFullName())
                .diemTrungBinh(diemTrungBinh)
                .dsDeThiDaLam(deThiInUserResponseArrayList)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<DeThiThongKeResponse> thongKe() {
        List<DeThiThongKeResponse> dsDeThiThongKeResponses = new ArrayList<>();
        for (DeThi x : deThiRepository.findAll()) {
            float diemTrungBinh = 0;
            for (KetQua ketQua : x.getKetQuas()) {
                diemTrungBinh += ketQua.getDiem();
            }
            diemTrungBinh = diemTrungBinh / (x.getKetQuas().size());
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            diemTrungBinh = Float.parseFloat(decimalFormat.format(diemTrungBinh));
            dsDeThiThongKeResponses.add(
                    DeThiThongKeResponse.builder()
                            .tenDeThi(x.getTen())
                            .tenKyThi(x.getKyThi().getTen())
                            .tenMonThi(x.getMonThi().getTenMon())
                            .trangThai(x.getTrangThai())
                            .soNguoiThamGia(x.getKetQuas().size())
                            .diemTrungBinh(diemTrungBinh)
                            .build()
            );
        }
        return dsDeThiThongKeResponses;
    }

//        DeThi deThi = deThiRepository.findById(deThiId)
//                .orElseThrow(() -> new AppException(ErrorCode.DETHI_NOT_EXISTED));
//        /**/DeThiThongKeResponse deThiThongKeResponse = new DeThiThongKeResponse();
//        Set<KetQuaResponse> dsKetQuaResponses = new HashSet<>();
//        float diemTrungBinh = 0;
//        for(KetQua x : deThi.getKetQuas()){
//            dsKetQuaResponses.add(ketQuaMapper.toKetQuaResponse(x));
//            diemTrungBinh += x.getDiem();
//        }
//        diemTrungBinh = diemTrungBinh/(deThi.getKetQuas().size());
//        DecimalFormat decimalFormat = new DecimalFormat("#.#");
//        diemTrungBinh = Float.parseFloat(decimalFormat.format(diemTrungBinh));
//        return DeThiThongKeResponse.builder()
//                .tenDeThi(deThi.getTen())
//                .diemTrungBinh(diemTrungBinh)
//                .soNguoiThamGia(deThi.getKetQuas().size())
//                .dsKetQua(dsKetQuaResponses)
//                .build();
}
