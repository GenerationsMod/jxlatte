package com.thebombzen.jxlatte.bundle.color;

public class ColorFlags {

    public static final int PRI_SRGB = 1;
    public static final int PRI_CUSTOM = 2;
    public static final int PRI_BT2100 = 9;
    public static final int PRI_P3 = 11;

    public static final int WP_D65 = 1;
    public static final int WP_CUSTOM = 2;
    public static final int WP_E = 10;
    public static final int WP_DCI = 11;

    public static final int CE_RGB = 0;
    public static final int CE_GRAY = 1;
    public static final int CE_XYB = 2;
    public static final int CE_UNKNOWN = 3;

    public static final int RI_PERCEPTUAL = 0;
    public static final int RI_RELATIVE = 1;
    public static final int RI_SATURATION = 2;
    public static final int RI_ABSOLUTE = 3;

    public static final int TF_BT709 = 1 + (1 << 24);
    public static final int TF_UNKNOWN = 2 + (1 << 24);
    public static final int TF_LINEAR = 8 + (1 << 24);
    public static final int TF_SRGB = 13 + (1 << 24);
    public static final int TF_PQ = 16 + (1 << 24);
    public static final int TF_DCI = 17 + (1 << 24);
    public static final int TF_HLG = 18 + (1 << 24);

    public static boolean validatePrimaries(int primaries) {
        return primaries == PRI_SRGB
            || primaries == PRI_CUSTOM
            || primaries == PRI_BT2100
            || primaries == PRI_P3;
    }

    public static boolean validateWhitePoint(int whitePoint) {
        return whitePoint == WP_D65
            || whitePoint == WP_CUSTOM
            || whitePoint == WP_E
            || whitePoint == WP_DCI;
    }



    public static boolean validateColorEncoding(int colorEncoding) {
        return colorEncoding >= 0 && colorEncoding <= 3;
    }

    public static boolean validateRenderingIntent(int renderingIntent) {
        return renderingIntent >= 0 && renderingIntent <= 3;
    }

    public static boolean validateTransfer(int transfer) {
        if (transfer < 0)
            return false;
        else if (transfer <= 10_000_000)
            return true;
        else if (transfer < (1 << 24))
            return false;
        else
            return transfer == TF_BT709
                || transfer == TF_UNKNOWN
                || transfer == TF_LINEAR
                || transfer == TF_SRGB
                || transfer == TF_PQ
                || transfer == TF_DCI
                || transfer == TF_HLG;
    }

    public static CIEXY getWhitePoint(int whitePoint) {
        switch (whitePoint) {
            case WP_D65:
                return new CIEXY(0.3127f, 0.3290f);
            case WP_E:
                return new CIEXY(1f/3f, 1f/3f);
            case WP_DCI:
                return new CIEXY(0.314f, 0.351f);
        }
        return null;
    }

    public static int getWhitePoint(CIEXY wp) {
        for (int white : new int[]{WP_D65, WP_E, WP_DCI}) {
            if (wp.matches(getWhitePoint(white)))
                return white;
        }
        return WP_CUSTOM;
    }

    public static CIEPrimaries getPrimaries(int primaries) {
        switch (primaries) {
            case ColorFlags.PRI_SRGB:
                return new CIEPrimaries(new CIEXY(0.639998686f, 0.330010138f),
                    new CIEXY(0.300003784f, 0.600003357f),
                    new CIEXY(0.150002046f, 0.059997204f));
            case ColorFlags.PRI_BT2100:
                return new CIEPrimaries(new CIEXY(0.708f, 0.292f),
                    new CIEXY(0.170f, 0.797f),
                    new CIEXY(0.131f, 0.046f));
            case ColorFlags.PRI_P3:
                return new CIEPrimaries(
                    new CIEXY(0.680f, 0.320f),
                    new CIEXY(0.265f, 0.690f),
                    new CIEXY(0.150f, 0.060f));
        }
        return null;
    }

    public static int getPrimaries(CIEPrimaries primaries) {
        for (int prim : new int[]{PRI_SRGB, PRI_BT2100, PRI_P3}) {
            if (primaries.matches(getPrimaries(prim)))
                return prim;
        }
        return PRI_CUSTOM;
    }
}
