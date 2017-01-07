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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Java FX fractal plotting example
 *
 * created by krzysztof.osiecki@icloud.com
 */
public class Main extends Application {

    private final static Logger log = Logger.getLogger(Main.class.getName());

    private PixelWriter pixelWriter;
    private final double step = 0.01;

    private Complex z = new Complex(0, 0);

    private int X_SIZE = 1000, Y_SIZE = 1000;

    /**
     * JavaFX startup method
     * @param primaryStage primary stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parameters params = getParameters();
        setSize(params);

        WritableImage writableImage = new WritableImage(X_SIZE, Y_SIZE);
        pixelWriter = writableImage.getPixelWriter();

        ImageView imageView = new ImageView();
        imageView.setImage(writableImage);

        VBox root = setupGui(imageView);

        primaryStage.setTitle("Mandelbrot or Julia");
        log.info("using x=" + X_SIZE + " y=" + Y_SIZE);
        primaryStage.setScene(new Scene(root, X_SIZE < 200 ? 200 : X_SIZE, Y_SIZE + 90));
        primaryStage.show();

        redrawFractal(new Complex(0,0));
    }

    private VBox setupGui(ImageView imageView) {
        GridPane rbar = new GridPane();
        Button rplus = new Button("R+");
        Button rminus = new Button("R-");
        Button iplus = new Button("I+");
        Button iminus = new Button("I-");
        Button reset = new Button("Mandelbrot");
        Button julia = new Button("Julia");
        Label re = new Label("z = ");

        rbar.add(rplus, 0, 0);
        rbar.add(rminus, 1, 0);
        rbar.add(iplus, 3, 0);
        rbar.add(iminus, 4, 0);
        rbar.add(reset, 6, 0);
        rbar.add(julia, 7, 0);
        rbar.add(re, 1, 1, 7, 1);

        Button quit = new Button("Quit");

        VBox root = new VBox();
        root.getChildren().addAll(imageView, rbar, quit);

        quit.setOnAction(e -> {
            Platform.exit();
        });

        rplus.setOnAction(e -> {
            z.setReal(z.getReal() + step);
            redrawFractal(z);
            re.setText("r = " + z.getReal() + ", " + z.getImaginary() + "j");
        });

        rminus.setOnAction(e -> {
            z.setReal(z.getReal() - step);
            redrawFractal(z);
            re.setText("r = " + z.getReal() + ", " + z.getImaginary() + "j");
        });

        iplus.setOnAction(e -> {
            z.setImaginary(z.getImaginary() + step);
            redrawFractal(z);
            re.setText("r = " + z.getReal() + ", " + z.getImaginary() + "j");
        });

        iminus.setOnAction(e -> {
            z.setImaginary(z.getImaginary() - step);
            redrawFractal(z);
            re.setText("r = " + z.getReal() + ", " + z.getImaginary() + "j");
        });

        reset.setOnAction(e -> {
            z = new Complex(0, 0);
            redrawFractal(z);
            re.setText("r = " + z.getReal() + ", " + z.getImaginary() + "j");
        });

        julia.setOnAction(e -> {
            z = new Complex(0.47, 0.30);
            redrawFractal(z);
            re.setText("r = " + z.getReal() + ", " + z.getImaginary() + "j");
        });

        return root;
    }

    private void redrawFractal(Complex z) {
        MandelbrotSet fractal;
        double MIN_R = -2;
        double MIN_I = -2;
        double MAX_R = -2;
        double MAX_I = 2;
        fractal = new MandelbrotSet(new Complex(MIN_R, MIN_I), new Complex(MAX_R, MAX_I), z);
        ConvergenceCalculator service = new ConvergenceCalculator();
        FractalPainter painter = new FractalPainter(service);
        ExecutorService exec = Executors.newFixedThreadPool(1);

        long startTime = System.currentTimeMillis();
        exec.submit(() -> Platform.runLater(() -> painter.drawFractal(0, X_SIZE - 1, pixelWriter, fractal, X_SIZE, Y_SIZE)));
        long endTime = System.currentTimeMillis();
        exec.shutdown();

        log.finer("done in " + (endTime - startTime) + "ms");
    }

    /**
     * Standard main method for console execution
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void setSize(Parameters params) {
        if (params.getNamed().containsKey("width")) {
            X_SIZE = Integer.decode(params.getNamed().get("width"));
        }
        if (params.getNamed().containsKey("height")) {
            Y_SIZE = Integer.decode(params.getNamed().get("height"));
        }
    }
}
