package com.trandat.btl_ltw.Controller;

import com.nimbusds.jose.JOSEException;
import com.trandat.btl_ltw.dto.request.AuthenticationRequest;
import com.trandat.btl_ltw.dto.request.IntrospectRequest;
import com.trandat.btl_ltw.dto.response.ApiResponse;
import com.trandat.btl_ltw.dto.response.AuthenticationResponse;
import com.trandat.btl_ltw.dto.response.IntrospectResponse;
import com.trandat.btl_ltw.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
         return ApiResponse.<AuthenticationResponse>builder()
                 .result(result)
                 .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

}
