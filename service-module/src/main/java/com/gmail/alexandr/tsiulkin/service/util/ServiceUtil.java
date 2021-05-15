package com.gmail.alexandr.tsiulkin.service.util;

import com.gmail.alexandr.tsiulkin.service.constant.PasswordGenerateConstant;
import com.gmail.alexandr.tsiulkin.service.model.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.gmail.alexandr.tsiulkin.service.constant.FormatConstant.DATE_FORMAT_PATTERN;
import static java.lang.Math.*;

public final class ServiceUtil {

    public static String getFormatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        return localDateTime.format(formatter);
    }

    public static String generateRandomPassword() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < PasswordGenerateConstant.NUMBER_OF_CHARS_IN_PASSWORD; i++) {
            int character = (random.nextInt(PasswordGenerateConstant.ALPHA_NUMERIC_STRING.length()));
            builder.append(PasswordGenerateConstant.ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static PageDTO getPageDTO(Integer page, double countReviews, int maximumObjectsOnPage) {
        PageDTO pageDTO = new PageDTO();
        double countOfPages = ceil(countReviews / maximumObjectsOnPage);
        pageDTO.setCountOfPages((long) countOfPages);
        long currentPage = (page + 1);
        pageDTO.setCurrentPage(currentPage);
        long beginPage = max(1, currentPage - 2);
        pageDTO.setBeginPage(beginPage);
        double endPage = min(beginPage + 2, countOfPages);
        pageDTO.setEndPage((long) endPage);
        int startPosition = (page - 1) * maximumObjectsOnPage;
        pageDTO.setStartPosition(startPosition);
        return pageDTO;
    }

}
