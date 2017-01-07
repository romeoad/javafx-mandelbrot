# JavaFX implementation of Mandelbrot and Julia set visualization

Hello, this is simple example of plotting Mandelbrot or Julia sets. It uses JavaFX, computes point colors asynchronously using Java concurrent features. There are some GUI controls that might not be self explanatory:

- `R+` increases real value of Z
- `R-` decreases real value of Z
- `I+` increases imaginary value of Z
- `I-` decreases imaginary value of Z

All in/decreases are done in `step` increments, which defaults to `0.01`. You can experiment with these values.

Mandelbrot button resets to plain old Mandelbrot set, Julia button resets to Julia set with precompiled default values. Quit simply quits application.

### Compiling

As usual with gradle:
```bash
$ gradle build
````
And then you will find executable jar in `build/libs`.

### Command line arguments

There are two command line arguments:

* width for setting canvas width (*if it is less then 200 it will become 200 due to label lengths*)
* height for setting canvas height (*a few pixels will be added to accomodate labels and buttons*)

Basically if you start it like:
```bash
$ java -jar javafx-mandelbrot-1.0.jar --width=1000 --height=1000
```
you will end up with 1000x1000 pixel canvas and buttons and labels below. Canvas are not resizable.

### Calculating colors

General rule of coloring pixels for Mandelbrot set is to check how many iterations would take for current coordinates to reach limit, and then color it accordingly. I modified slightly coloring procedure to enhance near-zero values represented by blacks. It very similar to gamma correction in your display.

#### Concurency

Long time ago I did similar routine which plotted Mandelbrot set. It was slow since machines were slow at that time and one of the methods to make things go faster was using a little more threads to compute. I noticed during development of this example that adding more computational threads that also paint their part in parallel does not shorten execution time. It even makes it slower on my box. Plotting default set with one thread took 2ms while with 8 threads it took 423ms. This was quite a surprise for me, but I suspect it is related to JavaFX usage.

#### License

This piece of software uses [Apache License](http://www.apache.org/licenses/LICENSE-2.0 "Apache License") version 2.0

#### References

[Mandelbrot set](https://en.wikipedia.org/wiki/Mandelbrot_set) article on Wikipedia
[Benoit Mandelbrot](https://en.wikipedia.org/wiki/Benoit_Mandelbrot) the fractalist, born in Warsaw just like me ;)