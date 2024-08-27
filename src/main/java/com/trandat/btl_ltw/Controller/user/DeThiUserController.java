package com.trandat.btl_ltw.Controller.user;

import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.DeThiDetailResponse;
import com.trandat.btl_ltw.dto.response.DeThiListCauHoiResponse;
import com.trandat.btl_ltw.dto.response.DeThiResponse;
import com.trandat.btl_ltw.service.DeThiService;
import com.trandat.btl_ltw.service.KyThiService;
import com.trandat.btl_ltw.service.MonThiService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "https://fe-quizz.vercel.app")
public class DeThiUserController {
    DeThiService deThiService;
    KyThiService kyThiService;
    MonThiService monThiService;

    @GetMapping("/getListCauHoiInDeThi")
    ApiResponse<DeThiListCauHoiResponse> getListCauHoiInDeThi(@RequestParam ("deThiId") Long deThiId){
        ApiResponse<DeThiListCauHoiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.getListCauHoi(deThiId));
        return apiResponse;
    }
    @GetMapping("/getAllDeThi")
    ApiResponse<List<DeThiDetailResponse>> getAllDeThiUser(){
        ApiResponse<List<DeThiDetailResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.getALLDeThiUSER());
        return apiResponse;
    }

    @GetMapping("/getDeThiInKyThi")
    ApiResponse<List<DeThiResponse>> getDeThiInkyThi(@RequestParam ("kyThiId") Long kyThiId){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(kyThiService.getDeThiInkyThi(kyThiId));
        return apiResponse;
    }

    @GetMapping("/getDeThiInMonThi")
    ApiResponse<List<DeThiResponse>> getDeThiInMonThi(@RequestParam ("monThiId") Long monThiId){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(monThiService.getDeThiInMonThi(monThiId));
        return apiResponse;
    }
    @GetMapping("/getDeThi")
    ApiResponse<List<DeThiResponse>> getDeThiInMonThi(@RequestParam ("status") Boolean status){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(deThiService.getDeThiStatus(status));
        return apiResponse;
    }
}
