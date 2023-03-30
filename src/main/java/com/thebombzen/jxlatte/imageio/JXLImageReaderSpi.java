package com.thebombzen.jxlatte.imageio;

import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

public class JXLImageReaderSpi extends ImageReaderSpi {
    private static final String vendorName = "Oracle Corporation";
    private static final String version = "1.0";
    private static final String[] names = { "jxl", "JXL" };
    private static final String[] suffixes = { "jxl" };
    private static final String[] MIMETypes = { "image/jxl", "image/jpg-xl" };
    private static final String readerClassName = JXLImageReaderSpi.class.getName();
    private static final String[] writerSpiNames = {};
    private static final byte[] LONG_HEADER = new byte[]{0x00, 0x00, 0x00, 0x0C, 0x4A, 0x58, 0x4C, 0x20};
    private byte[] bytes;

    public JXLImageReaderSpi() {
        super(vendorName,
                version,
                names,
                suffixes,
                MIMETypes,
                readerClassName,
                new Class<?>[] { ImageInputStream.class },
                writerSpiNames,
                false,
                null, null,
                null, null,
                true,
                "javax_imageio_jxl_1.0",
                "com.sun.imageio.plugins.png.PNGMetadataFormat",
                null, null
        );
    }

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (source instanceof InputStream is)
            return Arrays.equals(is.readNBytes(8), LONG_HEADER);
        if (source instanceof ImageInputStream is) {
            this.bytes = new byte[(int) is.length()];
            is.readFully(this.bytes);
            var headerBytes = new byte[LONG_HEADER.length];
            System.arraycopy(this.bytes, 0, headerBytes, 0, LONG_HEADER.length);
            return Arrays.equals(headerBytes, LONG_HEADER);
        }
        return false;
    }

    @Override
    public ImageReader createReaderInstance(Object extension) {
        return new JXLImageReader(this, bytes);
    }

    @Override
    public String getDescription(Locale locale) {
        return "Loads Jpeg XL files (.jxl)";
    }
}
