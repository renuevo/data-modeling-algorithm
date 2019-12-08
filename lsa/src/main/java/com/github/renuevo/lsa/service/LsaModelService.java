package com.github.renuevo.lsa.service;

import Jama.Matrix;
import com.github.renuevo.lsa.SvdDto;
import com.github.renuevo.lsa.modeling.DataRepository;
import com.github.renuevo.lsa.modeling.LsaModelComponent;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LsaModelService {

    private final DataRepository dataRepository;
    private final LsaModelComponent lsaModelComponent;

    /**
     * <pre>
     *  @methodName : LasExample
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-06 오후 11:13
     *  @summary : LSA Algorithm Example
     *  @param : []
     *  @return : void
     * </pre>
     */
    public void LasExample() {

        //Train Data Set Create
        List<Set<String>> trainDataList = dataRepository.createTrainDataSet(45);    //Meta Item 보다 크게 설정
        log.info("[============ Train Data Set ==============]");
        trainDataList.forEach(System.out::println); //Train Data Print

        //Train Matrix Data Set Create
        Matrix matrix = lsaModelComponent.createMatrix(trainDataList);
        Map<Integer, String> columnTitleMap = lsaModelComponent.getColumnMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        log.info("[============ Train Matrix Data Set ==============]");
        printMatrix(matrix, columnTitleMap);    //Train Matrix Data Print

        //Fix Matrix SVD -> S * V (column간 유사도)
        /*
        A = USV
        U : Row차원 공간에 Column만개의 단어에 대응되는 점으로 표현
        S : Column차원 공간에 Row만개의 문서에 대응되는 점으로 표현
        V : 차원의 중요도를 나타내는 대각행렬

        1. ROW - 차원간의 유사도 U X S 행렬의 row 간의 유사도로 계산한다.
        2. ROW와 Column - ROW S X V 행렬의 column 간의 유사도로 계산한다.
        3. Column - USV의 각 요소가 row와 column간의 유사도이다.
        */

        SvdDto svdDto = lsaModelComponent.fit(matrix, 5);
        Matrix columnSimilarity = svdDto.getMatrixS().times(svdDto.getMatrixV()); // S * V
        log.info("Column Similarity Matrix : " + columnSimilarity.getRowDimension() + " X " + columnSimilarity.getColumnDimension());

        double max = -1;
        double min = 1;
        String maxItem = "";
        String minItem = "";

        //LSA Analytics
        log.info("[==========" + columnTitleMap.get(0) + "의 유사도 =========]");
        List<Double> listA = Lists.newArrayList();
        List<Double> listB = Lists.newArrayList();
        for (int i = 0; i < columnSimilarity.getColumnDimension(); i++) {
            for (int j = 0; j < columnSimilarity.getRowDimension(); j++) {
                if (i == 0)
                    listA.add(columnSimilarity.get(j, i));
                else
                    listB.add(columnSimilarity.get(j, i));
            }

            if (listB.size() > 0) {

                double value = cosineSimilarity(listA, listB);
                log.info(columnTitleMap.get(i) + " : " + value);

                if(value > max) {
                    max = value;
                    maxItem = columnTitleMap.get(i) + " : " + value;
                }
                if(value < min){
                    min = value;
                    minItem = columnTitleMap.get(i) + " : " + value;
                }

                listB.clear();
            }
        }

        log.info("Similarity Max Item : "+maxItem);
        log.info("Similarity Min Item : "+minItem);
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
        double vectorSum;
        double word1Pow = 0;
        double word2Pow = 0;

        for (int i = 0; i < word1.size(); i++) {
            sum += word1.get(i) * word2.get(i);
            word1Pow += Math.pow(word1.get(i), 2);
            word2Pow += Math.pow(word2.get(i), 2);
        }
        vectorSum = Math.sqrt(word1Pow) * Math.sqrt(word2Pow);

        return sum / vectorSum;
    }

    /**
     * <pre>
     *  @methodName : printMatrix
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-07 오후 10:42
     *  @summary : Metrix Data Print
     *  @param : [matrix, columTitleMap]
     *  @return : void
     * </pre>
     */
    public void printMatrix(Matrix matrix, Map<Integer, String> columnTitleMap) {

        System.out.printf("%6s\t", "Matrix");

        for (int j = 0; j < columnTitleMap.size(); j++) {
            System.out.printf("%6s\t", columnTitleMap.get(j));
        }
        System.out.println();

        for (int i = 0; i < matrix.getRowDimension(); i++) {
            System.out.printf("%6s\t", "Row" + i);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                System.out.printf("%8s\t", matrix.get(i, j));
            }
            System.out.println();
        }
    }

}
