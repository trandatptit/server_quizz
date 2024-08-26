package com.trandat.btl_ltw.Controller.admin;

import com.trandat.btl_ltw.dto.request.DeThiCreationInputRequest;
import com.trandat.btl_ltw.dto.request.DeThiCreationRequest;
import com.trandat.btl_ltw.dto.request.DeThiUpdateRequest;
import com.trandat.btl_ltw.dto.response.*;
import com.trandat.btl_ltw.service.DeThiService;
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
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class DeThiAdminController {
    DeThiService deThiService;
    @PostMapping("/creatDeThi")
    ApiResponse<DeThiResponse> creatDeThi(
            @RequestParam("kyThiId") Long kyThiId,
            @RequestParam("monThiId") Long monThiId,
            @RequestBody DeThiCreationInputRequest request
            ){
        ApiResponse<DeThiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.creatDeThi(kyThiId, monThiId, request));
        return apiResponse;
    }

    @PutMapping("/updateDeThi/{deThiId}")
    ApiResponse<DeThiResponse> updateDeThi(@PathVariable Long deThiId, @RequestBody DeThiCreationInputRequest request){
        ApiResponse<DeThiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.updateDeThi(deThiId, request));
        return apiResponse;
    }

    @GetMapping("/getListCauHoiInDeThi")
    ApiResponse<DeThiListCauHoiResponse> getListCauHoiInDeThi(@RequestParam ("deThiId") Long deThiId){
        ApiResponse<DeThiListCauHoiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.getListCauHoi(deThiId));
        return apiResponse;
    }
    @GetMapping("/getAllDeThi")
    ApiResponse<List<DeThiDetailResponse>> getAllDeThi(){
        ApiResponse<List<DeThiDetailResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.getALLDeThi());
        return apiResponse;
    }

    @PutMapping("/updateCauHoiDeThi")
    ApiResponse<DeThiListCauHoiResponse> updateCauHoiDeThi(@RequestParam("deThiId") Long deThiId, @RequestBody DeThiUpdateRequest request){
        ApiResponse<DeThiListCauHoiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(deThiService.updateCauHoiDeThi(deThiId, request));
        return apiResponse;
    }

    @DeleteMapping("/deleteDeThi/{deThiId}")
    ApiResponse<String> deleteDeThi(@PathVariable Long deThiId){
        return ApiResponse.<String>builder()
                .message(deThiService.deleteDeThi(deThiId))
                .build();
    }

}
