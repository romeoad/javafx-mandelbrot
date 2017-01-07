/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mandelbrot;

/**
 * Convergence calculator for Mandelbrot and Julia set
 *
 * Created by krzysztof.osiecki@icloud.com on 06/01/17.
 */
public class ConvergenceCalculator {

    public int calculateConvergence(Complex c, Complex z, int steps) {
        int i = 0;
        while(i < steps) {
            double ziT = 2 * z.getImaginary() * z.getReal();
            double zT = z.getReal() * z.getReal() - z.getImaginary() * z.getImaginary();
            z.setReal(zT + c.getReal());
            z.setImaginary(ziT + c.getImaginary());

            if (z.abs() >= 4.0) {
                break;
            }
            i++;
        }
        return i;
    }

}
