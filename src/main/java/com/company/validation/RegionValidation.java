package com.company.validation;

import com.company.dto.RegionDto;
import com.company.exception.AppBadRequestException;

public class RegionValidation {

    public static void isValid(RegionDto dto){
        if (dto.getNameUz() == null || dto.getNameUz().trim().length() < 2) {
            throw new AppBadRequestException("name hato kiriltilgan");
        }

        if (dto.getNameEn().trim().length() < 2 || dto.getNameEn() == null) {
            throw new AppBadRequestException("password hato kiriltilgan");
        }

        if (dto.getNameRu() == null || dto.getNameRu().trim().length() < 2) {
            throw new AppBadRequestException("surname hato kiriltilgan");
        }
        if(dto.getKey() == null){
            throw new AppBadRequestException("key hato kiriltilgan");
        }
    }
}
