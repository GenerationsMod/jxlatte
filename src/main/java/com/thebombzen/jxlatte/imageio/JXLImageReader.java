package com.thebombzen.jxlatte.imageio;

import com.thebombzen.jxlatte.JXLDecoder;
import com.thebombzen.jxlatte.JXLImage;
import com.thebombzen.jxlatte.JXLOptions;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

public class JXLImageReader extends ImageReader {

    private final byte[] bytes;
    private JXLImage image;

    protected JXLImageReader(ImageReaderSpi originatingProvider, byte[] image) {
        super(originatingProvider);
        this.bytes = image;
    }

    @Override
    public int getNumImages(boolean allowSearch) {
        return 1;
    }

    @Override
    public int getWidth(int imageIndex) {
        return image.getWidth();
    }

    @Override
    public int getHeight(int imageIndex) {
        return image.getHeight();
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) {
        return null;
    }

    @Override
    public IIOMetadata getStreamMetadata() {
        return null;
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) {
        return null;
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        var decoder = new JXLDecoder(new ByteArrayInputStream(bytes), new JXLOptions());
        this.image = decoder.decode();

        var bufferedImage = new BufferedImage(getWidth(0), getHeight(0), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int r = (int) Math.min(Math.max(image.getBuffer()[0][y][x] * 255, 0), 255);
                int g = (int) Math.min(Math.max(image.getBuffer()[1][y][x] * 255, 0), 255);
                int b = (int) Math.min(Math.max(image.getBuffer()[2][y][x] * 255, 0), 255);
                int a = image.getAlphaIndex() == -1 ? 0xFF : (int) ((image.getBuffer()[3][y][x]) * 255);

                var argb = ((a & 0xFF) << 24) |
                        ((r & 0xFF) << 16) |
                        ((g & 0xFF) << 8)  |
                        ((b & 0xFF));
                bufferedImage.getRaster().setDataElements(0, 0, 1, 1, new int[]{argb});
            }
        }

        return bufferedImage;
    }
}
