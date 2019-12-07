package com.github.renuevo.lsa.modeling;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Repository
@AllArgsConstructor
public class DataRepository {

    private final Environment env;
    private final ResourceLoader resourceLoader;

    /**
     * <pre>
     *  @methodName : createTrainDataSet
     *  @author : Deokhwa.Kim
     *  @since : 2019-12-06 오전 12:33
     *  @summary : 랜덤한 트레이닝 데이터를 생성
     *  @param : [row]
     *  @return : void
     * </pre>
     */
    public List<Set<String>> createTrainDataSet(int row) {

        List<String> metaDataList;         //과일 목록 List
        List<Set<String>> trainDataList = Lists.newArrayList(); //Train Data Set List
        Set<String> rowSet;
        int column;

        //과일 목록 load 및 train data create
        try (Stream<String> lines = Files.lines(Path.of(resourceLoader.getResource(Objects.requireNonNull(env.getProperty("data.meta-data"))).getURI()))) {

            metaDataList = lines.collect(Collectors.toList());
            int length = (int) (Math.log10(metaDataList.size()) + 1);  //과일 리스트 자리수

            for (int i = 0; i < row; i++) {
                column = (int) (Math.random() * 10) + 1; //랜덤한 열 개수 생성
                rowSet = Sets.newHashSet();
                for (int j = 0; j < column; j++) {
                    rowSet.add(metaDataList.get(((int) (Math.random() * Math.pow(10, length)) % metaDataList.size())));
                }
                trainDataList.add(rowSet);
            }
        } catch (Exception e) {
            log.error("Train Data Create Error {}", e.getMessage(), e);
            e.printStackTrace();
        }

        return trainDataList;
    }

}
