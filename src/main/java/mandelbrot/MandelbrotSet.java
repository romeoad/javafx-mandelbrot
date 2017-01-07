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

import javafx.scene.paint.Color;

/**
 * Data for Mandelbrot and Julia set
 *
 * Created by krzysztof.osiecki@icloud.com on 06/01/17.
 */
public class MandelbrotSet {

    private double reMin, reMax, imMin, imMax, z = 0.0, zi = 0.0;
    private int convergenceSteps = 128;

    private Color convergenceColor = Color.WHITE;

    public boolean isMandelbrot() {
        return z == 0 && zi == 0;
    }

    public double getReMin() {
        return reMin;
    }

    public double getReMax() {
        return reMax;
    }

    public double getImMin() {
        return imMin;
    }

    public double getImMax() {
        return imMax;
    }

    public double getZ() {
        return z;
    }

    public double getZi() {
        return zi;
    }

    public int getConvergenceSteps() {
        return convergenceSteps;
    }

    public Color getConvergenceColor() {
        return convergenceColor;
    }

    public MandelbrotSet(Complex min, Complex max, Complex z) {
        reMin = min.getReal();
        imMin = min.getImaginary();
        reMax = max.getReal();
        imMax = max.getImaginary();
        this.z = z.getReal();
        zi = z.getImaginary();
    }
}
