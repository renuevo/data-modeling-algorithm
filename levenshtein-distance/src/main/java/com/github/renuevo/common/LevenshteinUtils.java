package com.github.renuevo.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class LevenshteinUtils {

    public static int getDistance(String s1, String s2) {
        StringBuilder stringBuilder = new StringBuilder();
        int longStrLen = s1.length() + 1;
        int shortStrLen = s2.length() + 1;
        int[] cost = new int[longStrLen];
        int[] newcost = new int[longStrLen];

        for (int i = 0; i < longStrLen; i++) {
            cost[i] = i;
            stringBuilder.append(i).append(" ");
        }

        log.info(stringBuilder.toString());

        for (int j = 1; j < shortStrLen; j++) {

            newcost[0] = j;
            for (int i = 1; i < longStrLen; i++) {
                int match = 0;
                if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                    match = 1;
                }

                // 대체, 삽입, 삭제의 비용을 계산한다
                int replace = cost[i - 1] + match;
                int insert = cost[i] + 1;
                int delete = newcost[i - 1] + 1;

                newcost[i] = Math.min(Math.min(insert, delete), replace);
            }
            cost = Arrays.copyOf(newcost, newcost.length);

            stringBuilder.setLength(0);
            for (int data : cost) {
                stringBuilder.append(data).append(" ");
            }

            log.info(stringBuilder.toString());

        }

        return cost[longStrLen - 1];
    }

}
