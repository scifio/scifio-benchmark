package org.knime.knip.scifio.profiling;

import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


/**
 * Created by Gabriel on 06/12/2014.
 */
public class TiffBenchmark {

    private static final String BASEFOLDER = "writetest/target/";
    private static final String IMG1 = "writetest/res/FakeTracks.tif";
    private static String BASENAME = "write_";
    private static String FORMAT = ".tif";

    @Before
    public void setup() throws IncompatibleTypeException, ImgIOException {

        //cleanup
        Path folder = Paths.get(BASEFOLDER);
        folder.toFile().delete();
        folder.toFile().mkdirs();
    }

    @Test
    public void tiffTest() throws ImgIOException, IncompatibleTypeException {

        ImgOpener opener = new ImgOpener();
        ImgSaver saver = new ImgSaver();
        List<SCIFIOImgPlus<?>> imgs;
        imgs = opener.openImgs(IMG1);
        SCIFIOImgPlus<?> sfimg = imgs.get(0);
        Img<?> img = sfimg.getImg();

        int numWrites = 100;
        for (int i = 0; i < numWrites; i++) {
            saver.saveImg(BASEFOLDER + BASENAME + i + FORMAT, img);
        }
    }

}
