package com.company.validation;

import com.company.dto.ArticleTypeDto;
import com.company.exception.AppBadRequestException;

public class ArticleTypeValidation {

    public static void isValid(ArticleTypeDto dto){
        if (dto.getNameUz() == null || dto.getNameUz().trim().length() < 2) {
            throw new AppBadRequestException("name hato kiriltilgan");
        }

        if (dto.getNameEn().trim().length() < 3 || dto.getNameEn() == null) {
            throw new AppBadRequestException("password hato kiriltilgan");
        }

        if (dto.getNameRu() == null || dto.getNameRu().trim().length() < 2) {
            throw new AppBadRequestException("surname hato kiriltilgan");
        }
    }
}
