package com.trandat.btl_ltw.Controller.admin;


import com.trandat.btl_ltw.dto.request.CauHoiCreationRequest;
import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.CauHoiResponse;
import com.trandat.btl_ltw.service.CauHoiService;
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
//@CrossOrigin(origins = "http://127.0.0.1:5500")
@CrossOrigin(origins = {"https://fe-quizz.vercel.app", "http://127.0.0.1:5500"})

public class CauHoiController {

    CauHoiService cauHoiService;

    @PostMapping("/{deThiId}/creatCauHoi")
    ApiResponse<List<CauHoiResponse>> creatCauHoi(@PathVariable Long deThiId, @RequestBody List<CauHoiCreationRequest> request){
        ApiResponse<List<CauHoiResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cauHoiService.creatCauHoi(deThiId, request));
        return apiResponse;
    }

    @PutMapping("/updateCauHoi/{cauHoiId}")
    ApiResponse<CauHoiResponse> updateCauHoi(@PathVariable Long cauHoiId, @RequestBody CauHoiCreationRequest request){
        ApiResponse<CauHoiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cauHoiService.updateCauHoi(cauHoiId, request));
        return apiResponse;
    }

    @DeleteMapping("/delete/{cauHoiId}")
    ApiResponse<String> deleteCauHoi(@PathVariable Long cauHoiId){
        cauHoiService.deleteCauHoi(cauHoiId);
        return ApiResponse.<String>builder()
                .message("da xoa cau hoi co id = " + cauHoiId)
                .build();
    }

}
