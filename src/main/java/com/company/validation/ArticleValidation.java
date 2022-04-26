package com.company.validation;

import com.company.dto.ArticleDto;
import com.company.exception.AppBadRequestException;

public class ArticleValidation {

    public static void isValid(ArticleDto dto){
        if (dto.getContent() == null) {
            throw new AppBadRequestException("Content hato kiriltilgan");
        }

        if ( dto.getDescription() == null) {
            throw new AppBadRequestException("Descripption hato kiriltilgan");
        }

        if (dto.getTitle() == null) {
            throw new AppBadRequestException("Tittle hato kiriltilgan");
        }

    }
}
