package br.com.askufcg.utils;

import br.com.askufcg.exceptions.BadRequestException;
import br.com.askufcg.exceptions.NotFoundException;

import java.util.Optional;

public class Util {
    public static void checkEntityNotFound(Optional entity, String errorMessage) {
        if (entity.isEmpty()){
            throw new NotFoundException(errorMessage);
        }
    }

    public static void checkEntityAlreadyExists(Optional entity, String errorMessage) {
        if (entity.isPresent()){
            throw new BadRequestException(errorMessage);
        }
    }
}
