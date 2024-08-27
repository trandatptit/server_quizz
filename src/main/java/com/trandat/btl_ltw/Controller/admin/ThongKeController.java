package com.trandat.btl_ltw.Controller.admin;

import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.ChiTietKetQuaResponse;
import com.trandat.btl_ltw.dto.response.DeThiThongKeResponse;
import com.trandat.btl_ltw.dto.response.UserThongKeResponse;
import com.trandat.btl_ltw.service.ThongKeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = {"https://fe-quizz.vercel.app", "http://127.0.0.1:5500"})
public class ThongKeController {
    ThongKeService thongKeService;

    @GetMapping("/ketquauser")
    ApiResponse<UserThongKeResponse> thongKeKetQuaUser(@RequestParam("userId") Long userId){
        ApiResponse<UserThongKeResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(thongKeService.thongKeUser(userId));
        return apiResponse;
    }
    @GetMapping("/chiTietKQ")
    ApiResponse<List<ChiTietKetQuaResponse>> chiTietKQ(@RequestParam ("ketQuaId") Long ketQuaId){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(thongKeService.chiTietKetQua(ketQuaId));
        return apiResponse;
    }

    @GetMapping("/thongke")
    ApiResponse<List<DeThiThongKeResponse>> thongKe(){
        ApiResponse<List<DeThiThongKeResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(thongKeService.thongKe());
        return apiResponse;
    }
}
