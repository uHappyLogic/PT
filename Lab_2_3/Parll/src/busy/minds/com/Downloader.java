package busy.minds.com;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Downloader {

    private static String IMAGE_PATH = "\\path\\to\\images";
    private static String IMAGE_URLS[] = {
        "https://upload.wikimedia.org/wikipedia/commons/5/5d/01R_26_October_2010.jpg", // 54 MB
        "https://upload.wikimedia.org/wikipedia/commons/0/05/01E_May_15_2013_1750Z.jpg", // 10 MB
        "https://upload.wikimedia.org/wikipedia/commons/f/fe/01R_Oct_12_2012_0905Z.jpg", // 12 MB
        "https://upload.wikimedia.org/wikipedia/commons/d/d2/01S_Dec_4_2011_0730Z.jpg" // 8 MB
    };

    private static BufferedImage resizeImage(BufferedImage image,
            int width, int height) {
        System.out.println("Resizing started");
        BufferedImage resizedImage = new BufferedImage(width, height,
                image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        System.out.println("Resizing finished");
        return resizedImage;
    }

    private static BufferedImage processImage(BufferedImage image) {
        System.out.println("Processing started");
        float[] blurKernelData = new float[64];
        Arrays.fill(blurKernelData, 1.0f / 64.0f);
        Kernel kernel = new Kernel(8, 8, blurKernelData);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage processedImage = op.filter(image, null);
        System.out.println("Processing finished");
        return processedImage;
    }

    private static BufferedImage downloadImage(String url) {
        try {
            System.out.println("Download started");
            BufferedImage image = ImageIO.read(new URL(url));
            System.out.printf("Image downloaded, resolution %dx%d\n",
                    image.getWidth(), image.getHeight());
            return image;
        } catch (IOException e) {
            System.out.println("Image downloading failed");
            e.printStackTrace();
            return null;
        }
    }

    private static String saveImage(BufferedImage image, String path) {
        try {
            System.out.println("Saving original image started");
            File outputfile = new File(path);
            ImageIO.write(image, "jpg", outputfile);
            System.out.println("Image saved");
        } catch (IOException e) {
            System.out.println("Image saving failed");
            e.printStackTrace();
        }
        return path;
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < IMAGE_URLS.length; i++) {
            BufferedImage image = downloadImage(IMAGE_URLS[i]);
            BufferedImage blurredImage = processImage(image);
            BufferedImage resized = resizeImage(blurredImage, 480, 640);
            saveImage(resized, IMAGE_PATH + "image_" + i + ".jpg");
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.printf("Finished processing after %.4f seconds\n",
                endTime / 1000.0);
    }
}
