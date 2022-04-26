package com.company.validation;

import com.company.dto.CategoryDto;
import com.company.exception.AppBadRequestException;

public class CategoryValidation {

    public static void isValid(CategoryDto dto){
        if (dto.getNameUz() == null || dto.getNameUz().trim().length() < 3) {
            throw new AppBadRequestException("name hato kiriltilgan");
        }

        if (dto.getNameEn().trim().length() < 2 || dto.getNameEn() == null) {
            throw new AppBadRequestException("password hato kiriltilgan");
        }

        if (dto.getNameRu() == null || dto.getNameRu().trim().length() < 2) {
            throw new AppBadRequestException("surname hato kiriltilgan");
        }
    }

}
