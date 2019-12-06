package com.github.renuevo.lsa.service;

import Jama.Matrix;
import com.github.renuevo.lsa.modeling.DataRepository;
import com.github.renuevo.lsa.modeling.LsaModelComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        List<Set<String>> trainDataList = dataRepository.createTrainDataSet(10);

        log.info("[============ Train Data Set ==============]");
        trainDataList.forEach(System.out::println); //Train Data Print


        //Train Matrix Data Set Create
        Matrix matrix = lsaModelComponent.createMatrix(trainDataList);

        log.info("[============ Train Matrix Data Set ==============]");
        printMatrix(matrix);    //Train Matrix Data Print

    }

    /**
     * <pre>
     *  @methodName : printMatrix
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-06 오후 11:13
     *  @summary : Metrix Data Print
     *  @param : [matrix]
     *  @return : void
     * </pre>
     */
    public void printMatrix(Matrix matrix) {

        System.out.printf("%6s\t", "Matrix");
        Map<Integer, String> columTitleMap = lsaModelComponent.getColumnMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        for (int j = 0; j < columTitleMap.size(); j++) {
            System.out.printf("%6s\t", columTitleMap.get(j));
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
