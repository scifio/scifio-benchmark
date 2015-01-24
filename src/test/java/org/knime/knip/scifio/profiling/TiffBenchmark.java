package org.knime.knip.scifio.profiling;

import io.scif.FormatException;
import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.imglib2.exception.IncompatibleTypeException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TiffBenchmark {

	private static final String BASEFOLDER = "writetest/target/";
	private static final String IMG1 = "writetest/res/FakeTracks.tif";
	private static final String BASENAME = "write_";
	private static final int NUM_WRITES = 300;

	private final ImgSaver m_saver = new ImgSaver();
	private SCIFIOImgPlus<?> m_sfimg;

	@Before
	public void setup() throws IncompatibleTypeException, ImgIOException {

		// cleanup
		final Path folder = Paths.get(BASEFOLDER);
		folder.toFile().delete();
		folder.toFile().mkdirs();

		final ImgOpener opener = new ImgOpener();
		List<SCIFIOImgPlus<?>> imgs = opener.openImgs(IMG1);
		m_sfimg = imgs.get(0);
	}
	
	@After
	public void cleanup() throws Exception {
		final Path folder = Paths.get(BASEFOLDER);
		folder.toFile().delete();
	}

	@Test
	public void tiffNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(BASEFOLDER + BASENAME + i + ".tif", m_sfimg);
		}
	}

	@Test
	public void pngNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(BASEFOLDER + BASENAME + i + ".png", m_sfimg);
		}
	}

	@Test
	public void epsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(BASEFOLDER + BASENAME + i + ".eps", m_sfimg);
		}
	}

	@Test
	public void icsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(BASEFOLDER + BASENAME + i + ".ics", m_sfimg);
		}
	}

	@Test
	public void jpgNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(BASEFOLDER + BASENAME + i + ".jpg", m_sfimg);
		}
	}

//	@Test
//	public void jp2NaiveTest() throws ImgIOException,
//			IncompatibleTypeException, FormatException {
//
//		for (int i = 0; i < NUM_WRITES; i++) {
//			m_saver.saveImg(BASEFOLDER + BASENAME + i + ".jp2", m_sfimg);
//		}
//	}

}
