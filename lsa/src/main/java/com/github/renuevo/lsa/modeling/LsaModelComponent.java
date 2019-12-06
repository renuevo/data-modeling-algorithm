package com.github.renuevo.lsa.modeling;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class LsaModelComponent {

    @Getter
    Map<String, Integer> columnMap = Maps.newHashMap();

    public SingularValueDecomposition fit(Matrix matrix) {

        SingularValueDecomposition singularValueDecomposition = null;

        return singularValueDecomposition;
    }

    /**
     * <pre>
     *  @methodName : createMatrix
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-06 오후 10:47
     *  @summary : Training Matrix 데이터 생성
     *  @param : [trainDataList]
     *  @return : Jama.Matrix
     * </pre>
     */
    public Matrix createMatrix(List<Set<String>> trainDataList) {

        //Column Data Hash Index
        columnMap.clear();
        for (Set<String> set : trainDataList) {
            set.forEach(it -> {
                if (!columnMap.containsKey(it))
                    columnMap.put(it, columnMap.size());
            });
        }

        //Create Matrix Size
        Matrix matrix = new Matrix(trainDataList.size(), columnMap.size());

        //Create Train Matrix Data
        for (int i = 0; i < trainDataList.size(); i++) {
            for (String key : columnMap.keySet()) {

                int value = 0;
                if (trainDataList.get(i).contains(key))
                    value = 1;

                matrix.set(i, columnMap.get(key), value);
            }
        }
        return matrix;
    }

    /**
     * <pre>
     *  @methodName : cosineSimilarity
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-06 오전 12:16
     *  @summary : cosine 거리 계산
     *  @param : [word1, word2]
     *  @return : double
     * </pre>
     */
    private double cosineSimilarity(List<Double> word1, List<Double> word2) {
        double sum = 0;
        double vectorSum = 0;
        double word1Pow = 0;
        double word2Pow = 0;

        for (int i = 0; i < word1.size(); i++) {
            sum += word1.get(i) * word2.get(i);
            word1Pow += Math.pow(word1.get(i), 2);
            if (Double.isNaN(word1Pow))
                log.warn("Is Nan Data : " + word1.get(i));
            word2Pow += Math.pow(word2.get(i), 2);
        }
        vectorSum = Math.sqrt(word1Pow) * Math.sqrt(word2Pow);

        return sum / vectorSum;
    }
}
