package com.thebombzen.jxlatte.bundle;

public interface ExtraChannelType {
    
    int ALPHA = 0;
    int DEPTH = 1;
    int SPOT_COLOR = 2;
    int SELECTION_MASK = 3;
    int CMYK_BLACK = 4;
    int COLOR_FILTER_ARRAY = 5;
    int THERMAL = 6;
    int NON_OPTIONAL = 15;
    int OPTIONAL = 16;

    static boolean validate(int ec) {
        return ec >= 0 && ec <= 6 || ec == 15 || ec == 16;
    }
}
