package com.github.renuevo.service;

import com.github.renuevo.common.LevenshteinUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LevenshteinService {

    public void levenshteinExample() {
        String text1 = "유사도나 분석 할까요";
        String text2 = "얼마나 분석이 될까요";
        int ex1 = LevenshteinUtils.getDistance(text1, text2);
        log.info(text1 + " / " + text2 + " = " + ex1);
    }
}
