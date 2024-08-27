package com.trandat.btl_ltw.Controller.admin;


import com.trandat.btl_ltw.dto.request.MonThiRequest;
import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.MonThiDeTailResponse;
import com.trandat.btl_ltw.dto.response.MonThiResponse;
import com.trandat.btl_ltw.service.MonThiService;
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
@CrossOrigin(origins = "https://fe-quizz.vercel.app")
public class MonThiController {
    MonThiService monThiService;

    @PostMapping("/creatMonThi")
    ApiResponse<MonThiResponse> creatMonThi(@RequestBody MonThiRequest request){
        ApiResponse<MonThiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(monThiService.creatMonThi(request));
        return apiResponse;
    }

    @PutMapping( "/updateMonThi/{monThiId}")
    ApiResponse<MonThiResponse> updateMonThi(@PathVariable Long monThiId, @RequestBody MonThiRequest request){
        ApiResponse<MonThiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(monThiService.updateMonThi(monThiId, request));
        return apiResponse;
    }

    @DeleteMapping("/deleteMonThi/{monThiId}")
    ApiResponse<String> deleteMonThi(@PathVariable Long monThiId){
        return ApiResponse.<String>builder()
                .message(monThiService.deleteMonThi(monThiId))
                .build();
    }

    @GetMapping("/getAllMonThi")
    ApiResponse<List<MonThiDeTailResponse>> getAllMonThi(){
        return ApiResponse.<List<MonThiDeTailResponse>>builder()
                .result(monThiService.getAllMonThi())
                .build();
    }

}
