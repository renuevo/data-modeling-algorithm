package com.github.renuevo.service;

import com.github.renuevo.common.LevenshteinUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class LevenshteinService {

    public void levenshteinExample() {
        String text1 = "글자테스트";
        String text2 = "한글테스트";

        int ex1 = LevenshteinUtils.getDistance(text1, text2);
        log.info(text1 + " / " + text2 + " = " + ex1);
    }
}
