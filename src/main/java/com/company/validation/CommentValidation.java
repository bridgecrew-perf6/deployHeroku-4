package com.company.validation;

import com.company.dto.ComentDto;
import com.company.exception.AppBadRequestException;

public class CommentValidation {


    public static void isValid(ComentDto dto){

        if (dto.getTitle() == null) {
            throw new AppBadRequestException("Tittle hato kiriltilgan");
        }

    }
}
