package com.thepokecraftmod;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class jxlTest {

    public static void main(String[] args) throws IOException {
        var start = System.currentTimeMillis();
        var image = ImageIO.read(new File("samples/body_alb.jxl"));
        //System.out.println("Image Read Took " + (System.currentTimeMillis() - start) + "ms");

        ImageIO.write(image, "png", new File("out.png"));
    }
}
