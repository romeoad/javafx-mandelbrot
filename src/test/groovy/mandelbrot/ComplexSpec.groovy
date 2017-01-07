package mandelbrot

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Complex class tests/specification
 *
 * Created by krzysztof.osiecki@icloud.com on 07/01/17.
 */
class ComplexSpec extends Specification {

    @Unroll
    def "complex number constructor with (#real, #imaginary)"() {
        given:
        Complex n = new Complex(real, imaginary)

        expect:
        n.getReal() == real
        n.getImaginary() == imaginary

        where:
        real             | imaginary
        0.0d             | 0.0d
        13.0d            | 7.0091d
        Double.MAX_VALUE | Double.MAX_VALUE
        Double.MIN_VALUE | Double.MIN_VALUE
    }

    @Unroll
    def "abs for (#real, #imaginary) complex"() {
        given:
        Complex n = new Complex(real, imaginary)

        expect:
        n.abs() == result

        where:
        real | imaginary || result
        0    | 0         || 0
        1    | 1         || 2
        5    | 1         || 26
        10   | 10        || 200
        -1   | 1         || 2
        -2   | -2        || 8
    }
}
