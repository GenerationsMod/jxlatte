package com.thebombzen.jxlatte.frame;

import java.io.IOException;

import com.thebombzen.jxlatte.frame.lfglobal.GlobalModular;
import com.thebombzen.jxlatte.frame.modular.ModularChannel;
import com.thebombzen.jxlatte.frame.modular.ModularStream;
import com.thebombzen.jxlatte.io.Bitreader;

public class LFGroup {
    public final ModularStream lfStream;
    public final LFCoefficients lfCoeff;
    public final HFCoefficients hfCoeff;
    public LFGroup(Bitreader reader, Frame parent, int index, ModularChannel[] replaced) throws IOException {
        GlobalModular gModular = parent.getLFGlobal().gModular;

        lfStream = new ModularStream(reader, gModular.globalTree, parent,
            1 + parent.getNumLFGroups() + index, replaced);
        lfStream.decodeChannels(reader, false);
        lfStream.applyTransforms();
        if (parent.getFrameHeader().encoding == FrameFlags.VARDCT) {
            this.lfCoeff = new LFCoefficients(reader);
            this.hfCoeff = new HFCoefficients(reader);
            throw new UnsupportedOperationException("VarDCT currently not implemented");
        } else {
            this.lfCoeff = null;
            this.hfCoeff = null;
        }
    }
}