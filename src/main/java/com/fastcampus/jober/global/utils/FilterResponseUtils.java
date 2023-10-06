package com.fastcampus.jober.global.utils;

import com.fastcampus.jober.global.utils.api.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FilterResponseUtils {

    public static void setErrorResponse(
        HttpStatus status,
        HttpServletResponse httpServletResponse,
        Throwable e
    ) throws IOException {

        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDto = new ResponseDTO<>(
            status,
            e.getMessage()
        );
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDto);
        httpServletResponse.getOutputStream().write(responseBody.getBytes());
    }
}
