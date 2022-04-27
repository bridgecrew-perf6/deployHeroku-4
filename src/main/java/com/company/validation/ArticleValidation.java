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

        if(dto.getRegionId() == null){
            throw new AppBadRequestException("Region id null");
        }

        if(dto.getTypeId() == null){
            throw new AppBadRequestException("Type id null");
        }

        if(dto.getCategoryId() == null){
            throw new AppBadRequestException("Category id null");
        }

    }
}
