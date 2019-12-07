package com.github.renuevo.lsa;

import Jama.Matrix;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <pre>
 * @className : SvdDto
 * @author : Deokhwa.Kim
 * @since : 2019-12-07
 * </pre>
 */
@Data
@AllArgsConstructor
public class SvdDto {

    Matrix matrixU;
    Matrix matrixS;
    Matrix matrixV;

}
