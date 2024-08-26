package com.trandat.btl_ltw.Controller.user;

import com.trandat.btl_ltw.dto.request.CauTraLoiRequest;
import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.KetQuaResponse;
import com.trandat.btl_ltw.service.KetQuaService;
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
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class KetQuaUserController {
    KetQuaService ketQuaService;

    @PostMapping("/ketqua")
    ApiResponse<KetQuaResponse> ketQua(
            @RequestParam ("deThiId") Long deThiId,
            @RequestBody List<CauTraLoiRequest> request
            ){
        ApiResponse<KetQuaResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(ketQuaService.ketQua(deThiId, request));
        return apiResponse;
    }
}
