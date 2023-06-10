package com.thebombzen.jxlatte.frame;

public interface FrameFlags {
    int REGULAR_FRAME = 0;
    int LF_FRAME = 1;
    int REFERENCE_ONLY = 2;
    int SKIP_PROGRESSIVE = 3;

    int VARDCT = 0;
    int MODULAR = 1;

    int NOISE = 1;
    int PATCHES = 2;
    int SPLINES = 16;
    int USE_LF_FRAME = 32;
    int SKIP_ADAPTIVE_LF_SMOOTHING = 128;

    int BLEND_REPLACE = 0;
    int BLEND_ADD = 1;
    int BLEND_BLEND = 2;
    int BLEND_MULADD = 3;
    int BLEND_MULT = 4;
}
