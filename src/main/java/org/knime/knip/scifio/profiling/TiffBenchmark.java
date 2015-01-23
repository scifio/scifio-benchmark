package org.knime.knip.scifio.profiling;

import io.scif.Format;
import io.scif.FormatException;
import io.scif.SCIFIO;
import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.imglib2.exception.IncompatibleTypeException;

import org.junit.Before;
import org.junit.Test;
import org.scijava.Context;

/**
 * Created by Gabriel on 06/12/2014.
 */
public class TiffBenchmark {

    private static final String BASEFOLDER = "writetest/target/";
    private static final String IMG1 = "writetest/res/FakeTracks.tif";
    private static String BASENAME = "write_";
    private static String FORMAT = ".tif";

    /* map from the writer names to the actual writers */
    private HashMap<String, Format> m_mapFormats = null;
    private final io.scif.SCIFIO SCIFIO = new SCIFIO();
    private ImgSaver m_saver;
    private String[] m_writers;

    @Before
    public void setup() throws IncompatibleTypeException, ImgIOException {

        // cleanup
        final Path folder = Paths.get(BASEFOLDER);
        folder.toFile().delete();
        folder.toFile().mkdirs();
    }


    @Test
    public void tiffNaiveTest() throws ImgIOException,
            IncompatibleTypeException, FormatException {

        final ImgOpener opener = new ImgOpener();
        List<SCIFIOImgPlus<?>> imgs = opener.openImgs(IMG1);
        final SCIFIOImgPlus<?> sfimg = imgs.get(0);
//        final Img<?> img = sfimg.getImg();

        final int numWrites = 100;
        for (int i = 0; i < numWrites; i++) {
            m_saver.saveImg(BASEFOLDER + BASENAME + i + FORMAT, sfimg);
        }
    }

}
