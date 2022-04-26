package com.company.validation;


import com.company.dto.ProfileDto;
import com.company.exception.AppBadRequestException;

public class ProfileValidation {

    public static void isValid(ProfileDto dto) {

        if (dto.getName() == null || dto.getName().length() < 3) {
            throw new AppBadRequestException("name hato kiriltilgan");
        }

        if (dto.getPassword().trim().length() < 8 || dto.getPassword() == null) {
            throw new AppBadRequestException("password hato kiriltilgan");
        }

        if (dto.getSurname() == null || dto.getSurname().length() < 3) {
            throw new AppBadRequestException("surname hato kiriltilgan");
        }

        if (dto.getEmail() == null ||!dto.getEmail().contains("@")) {
            throw new AppBadRequestException("email hato kiriltilgan");
        }
        if(dto.getRole() == null){
            throw new AppBadRequestException("role si hato");
        }
    }

}
