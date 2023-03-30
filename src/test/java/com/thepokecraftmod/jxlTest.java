package com.thepokecraftmod;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class jxlTest {

    public static void main(String[] args) throws IOException {
        while (true) {
            var start = System.currentTimeMillis();
            var image = ImageIO.read(new File("samples/ants.jxl"));
            System.out.println("Image Read Took " + (System.currentTimeMillis() - start) + "ms");
        }
        //ImageIO.write(image, "png", new File("cool.png"));
    }
}
