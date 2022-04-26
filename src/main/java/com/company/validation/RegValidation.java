package com.company.validation;

import com.company.dto.RegDto;
import com.company.exception.AppBadRequestException;

public class RegValidation {

    public static void isValid(RegDto dto){
        if (dto.getName() == null || dto.getName().trim().length() < 2) {
            throw new AppBadRequestException("name hato kiriltilgan");
        }

        if (dto.getSurname().trim().length() < 2 || dto.getSurname() == null) {
            throw new AppBadRequestException("password hato kiriltilgan");
        }

        if (dto.getPassword() == null || dto.getPassword().trim().length() < 8) {
            throw new AppBadRequestException("surname hato kiriltilgan");
        }
        if(dto.getEmail() == null || !dto.getEmail().contains("@") || dto.getEmail().trim().length() < 5){
            throw new AppBadRequestException("email hato kiriltilgan");
        }


    }
}
