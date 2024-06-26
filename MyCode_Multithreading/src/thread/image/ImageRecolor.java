package thread.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ImageRecolor {
    private static final String SOURCE = "./resources/many-flowers.jpg";
    private static final String DESTINATION = "./out/many-flowers.jpg";
    private static final int NUMBER_OF_THREADS = 2;

    public static void main(String[] args) throws IOException {
        BufferedImage initialImage = ImageIO.read(new File(SOURCE));
        BufferedImage finalImage = new BufferedImage(initialImage.getWidth(), initialImage.getHeight(),
                                                     BufferedImage.TYPE_INT_RGB);

        long start = System.currentTimeMillis();
        singleThreadedSolution(initialImage, finalImage);
//        multithreadedSolution(initialImage, finalImage, NUMBER_OF_THREADS);
        long end = System.currentTimeMillis();

        System.out.println("Single threaded solution time: " + (end - start));
//        System.out.println("multithreadedSolution took " + (end - start) + "ms");

        File outputFile = new File(DESTINATION);
        ImageIO.write(finalImage, "jpg", outputFile);
    }

    private static void multithreadedSolution(BufferedImage initialImage, BufferedImage finalImage,
                                              int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = initialImage.getWidth();
        int height = initialImage.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int leftCorner = 0;
                int topCorner = height * threadMultiplier;

                recolorImage(initialImage, finalImage, leftCorner, topCorner, width, height);
            });

            threads.add(thread);
        }

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void singleThreadedSolution(BufferedImage initialImage, BufferedImage finalImage) {
        recolorImage(initialImage, finalImage, 0, 0, initialImage.getWidth(),
                     initialImage.getHeight());
    }

    private static void recolorImage(BufferedImage initialImage, BufferedImage finalImage, int leftCorner,
                                     int topCorner, int width, int height) {
        for(int x = leftCorner ; x < leftCorner + width && x < initialImage.getWidth() ; x++ ) {
            for (int y = topCorner ; y < topCorner + height && y < initialImage.getHeight() ; y++ ) {
                recolorPixels(initialImage, finalImage, x, y);
            }
        }
    }

    private static void recolorPixels(BufferedImage initialImage, BufferedImage finalImage, int x, int y) {
        int rgb = initialImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int recoloredRed;
        int recoloredGreen;
        int recoloredBlue;

        if (isShadeOfGrey(red, green, blue)){
            recoloredRed = Math.min(255, red + 10);
            recoloredGreen = Math.max(0, green - 80);
            recoloredBlue = Math.max(0, blue - 20);
        } else {
          recoloredRed = red;
          recoloredGreen = green;
          recoloredBlue = blue;
        }

        int recoloredRGB = buildRGB(recoloredRed, recoloredGreen, recoloredBlue);
        //> Check if works
        finalImage.setRGB(x, y, recoloredRGB);
    }

    private static boolean isShadeOfGrey(int red, int green, int blue) {
        return Math.abs(red - green) <= 30
                && Math.abs(green - blue) <= 30
                && Math.abs(blue - red) <= 30;
    }

    private static int buildRGB(int red, int green, int blue) {
        int rgb = 0x00000000;

        int alpha = 0xFF000000;
        int shiftedRed = red << 16;
        int shiftedGreen = green << 8;
        int shiftedBlue = blue;

        rgb |= alpha;
        rgb |= shiftedRed;
        rgb |= shiftedGreen;
        rgb |= shiftedBlue;

        return rgb;
    }

    private static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    private static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    private static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
