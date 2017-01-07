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

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

/**
 * Painter for Mandelbrot and Julia fractals
 *
 * Created by krzysztof.osiecki@icloud.com on 06/01/17.
 */
public class FractalPainter {

    private ConvergenceCalculator calculator;

    public FractalPainter(ConvergenceCalculator calculator) {
        this.calculator = calculator;
    }

    public void drawFractal(int start, int end, PixelWriter writer, MandelbrotSet fractal, int X_SIZE, int Y_SIZE) {
        double precision = Math.max(
                (fractal.getReMax() - fractal.getReMin()) / X_SIZE,
                (fractal.getImMax() - fractal.getImMin()) / Y_SIZE);

        double convergenceVal;
        for (double c = fractal.getReMin() + (precision * start), xR = start; xR <= end; c += precision, xR++) {
            for (double ci = fractal.getImMin(), yR = 0; yR < Y_SIZE; ci += precision, yR++) {
                if (fractal.isMandelbrot()) {
                    convergenceVal = calculator.calculateConvergence(new Complex(c, ci), new Complex(fractal.getZ(), fractal.getZi()), fractal.getConvergenceSteps());
                } else { // isJulia
                    convergenceVal = calculator.calculateConvergence(new Complex(fractal.getZi(), fractal.getZ()), new Complex(ci, c), fractal.getConvergenceSteps());
                }

                double t1 =  convergenceVal / fractal.getConvergenceSteps();
                double withGamma = 1 - (t1 - 1) * (t1 - 1) * (t1 - 1) * (t1 - 1);

                if (convergenceVal < fractal.getConvergenceSteps())
                    writer.setColor((int)xR, (int)yR, Color.color(withGamma * 0.3, withGamma * 0.6, withGamma));
                else
                    writer.setColor((int)xR, (int)yR, fractal.getConvergenceColor());
            }
        }

    }
}
