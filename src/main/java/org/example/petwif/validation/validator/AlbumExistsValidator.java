package org.example.petwif.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.petwif.apiPayload.code.status.ErrorStatus;
import org.example.petwif.domain.entity.Album;
import org.example.petwif.service.albumService.AlbumQueryService;
import org.example.petwif.validation.annotation.ExistAlbum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlbumExistsValidator implements ConstraintValidator<ExistAlbum, Long> {

    private final AlbumQueryService albumQueryService;

    @Override
    public void initialize(ExistAlbum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long albumId, ConstraintValidatorContext context) {
        Optional<Album> target = albumQueryService.findAlbum(albumId);

        if (target.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ALBUM_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
