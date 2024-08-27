package com.trandat.btl_ltw.Controller.admin;

import com.trandat.btl_ltw.dto.request.KyThiRequest;
import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.KyThiDetailResponse;
import com.trandat.btl_ltw.dto.response.KyThiResponse;
import com.trandat.btl_ltw.service.KyThiService;
import jakarta.validation.Valid;
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
public class KyThiController {
    KyThiService kyThiService;

    @PostMapping("/creatKyThi")
    ApiResponse<KyThiResponse> creatKyThi(@RequestBody @Valid KyThiRequest kyThi){
        ApiResponse<KyThiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(kyThiService.creatKyThi(kyThi));
        return apiResponse;
    }

    @PutMapping("/updateKyThi/{kyThiId}")
    ApiResponse<KyThiResponse> updateKyThi(@PathVariable Long kyThiId, @RequestBody KyThiRequest kythi){
        ApiResponse<KyThiResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(kyThiService.updateKyThi(kyThiId, kythi));
        return apiResponse;
    }

    @DeleteMapping("/deleteKyThi/{kyThiId}")
    ApiResponse<String> deleteMonThi(@PathVariable Long kyThiId){
        return ApiResponse.<String>builder()
                .message(kyThiService.deleteKyThi(kyThiId))
                .build();
    }


    @GetMapping("/getAllKyThi")
    ApiResponse<List<KyThiDetailResponse>> getAllKyThi(){
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}", authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<KyThiDetailResponse>>builder()
                .result(kyThiService.getAllKyThi())
                .build();
    }
}
