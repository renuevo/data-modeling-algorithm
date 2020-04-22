package com.github.renuevo.common;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class LevenshteinUtils {

    /**
     * <pre>
     *  @methodName : getDistance
     *  @author : Deokhwa.Kim
     *  @since : 2020-04-23 오전 12:34
     *  @summary : 레벤슈타인 거리 알고리즘
     *  @param : [s1, s2]
     *  @return : int
     * </pre>
     */
    public static int getDistance(String s1, String s2) {
        StringBuilder stringBuilder = new StringBuilder();  //출력 확인용
        List<Integer> costList = Lists.newArrayList();
        List<Integer> newCostList = Lists.newArrayList();

        //행렬 첫줄 초기화
        for (int i = 0; i <= s1.length(); i++) {
            costList.add(i);
            stringBuilder.append(i).append(" ");
        }

        log.info(stringBuilder.toString());

        int matchCost;
        for (int i = 1; i <= s2.length(); i++) {
            newCostList.add(0, i);
            for (int j = 1; j < costList.size(); j++) {

                if (s1.charAt(j - 1) != s2.charAt(i - 1)) matchCost = 1;
                else matchCost = 0;

                // 대체, 삽입, 삭제의 비용을 계산한다
                int replace = costList.get(j - 1) + matchCost; //변경 비용
                int insert = costList.get(j) + 1;                //삽입 비용
                int delete = newCostList.get(j - 1) + 1;           //삭제 비용

                newCostList.add(j, Ints.min(replace, insert, delete));  //최소 비용 계산
            }

            Collections.copy(costList, newCostList);    //행렬 줄바꿈
            newCostList.clear();

            stringBuilder.setLength(0); //출력 초기화

            //행렬 출력
            costList.forEach(data -> stringBuilder.append(data).append( " "));
            log.info(stringBuilder.toString());
        }

       return costList.get(costList.size() - 1);
    }

}
