package com.thepokecraftmod;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class jxlTest {

    public static void main(String[] args) throws IOException {
        var image = ImageIO.read(new File("samples/body_alb.jxl"));
        image = ImageIO.read(new File("samples/body_rgn.jxl"));

        ImageIO.write(image, "png", new File("out.png"));
    }
}
