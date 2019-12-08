package com.github.renuevo.lsa.modeling;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import com.github.renuevo.lsa.SvdDto;
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

    /**
     * <pre>
     *  @methodName : fit
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-07 오후 10:49
     *  @summary : SVD 차원 축소
     *  @param : [matrix, dimension]
     *  @return : com.github.renuevo.lsa.SvdDto
     * </pre>
     */
    public SvdDto fit(Matrix matrix, int dimension) {
        SingularValueDecomposition singularValueDecomposition = matrix.svd();
        Matrix matrixU = singularValueDecomposition.getU();
        Matrix matrixS = singularValueDecomposition.getS();
        Matrix matrixV = singularValueDecomposition.getV();

        log.info("[============ Matrix Dimension Print ==============]");
        log.info("MatrixU : " + matrixU.getRowDimension() + " X " + matrix.getColumnDimension());
        log.info("MatrixS : " + matrixS.getRowDimension() + " X " + matrixS.getColumnDimension());
        log.info("MatrixV : " + matrixV.getRowDimension() + " X " + matrixV.getColumnDimension());

        matrixU = matrixU.getMatrix(0, matrixU.getColumnDimension() - 1, 0, dimension);
        matrixS = matrixS.getMatrix(0, dimension, 0, dimension);
        matrixV = matrixV.getMatrix(0, dimension, 0, matrixV.getColumnDimension() - 1);
        log.info("MatrixU Reduction: " + matrixU.getRowDimension() + " X " + matrixU.getColumnDimension());
        log.info("MatrixS Reduction: " + matrixS.getRowDimension() + " X " + matrixS.getColumnDimension());
        log.info("MatrixV Reduction: " + matrixV.getRowDimension() + " X " + matrixV.getColumnDimension());

        return new SvdDto(matrixU, matrixS, matrixV);
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
        Matrix matrix = null;
        try {
            //Column Data Hash Index
            columnMap.clear();
            for (Set<String> set : trainDataList) {
                set.forEach(it -> {
                    if (!columnMap.containsKey(it))
                        columnMap.put(it, columnMap.size());
                });
            }

            if (columnMap.size() > trainDataList.size())
                throw new Exception("Column is greater than Row");

            //Create Matrix Size
            matrix = new Matrix(trainDataList.size(), columnMap.size());

            //Create Train Matrix Data
            for (int i = 0; i < trainDataList.size(); i++) {
                for (String key : columnMap.keySet()) {

                    int value = 0;
                    if (trainDataList.get(i).contains(key))
                        value = 1;

                    matrix.set(i, columnMap.get(key), value);
                }
            }
        } catch (Exception e) {
            log.error("Create Matrix Error {}", e.getMessage(), e);
            e.printStackTrace();
        }

        return matrix;
    }

}
