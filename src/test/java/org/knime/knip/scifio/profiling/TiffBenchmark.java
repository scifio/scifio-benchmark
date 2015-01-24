package org.knime.knip.scifio.profiling;

import static org.junit.Assert.assertTrue;
import io.scif.FormatException;
import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import java.io.File;
import java.util.List;

import net.imglib2.exception.IncompatibleTypeException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scijava.util.FileUtils;

public class TiffBenchmark {

	private static final String IMG1 = "/FakeTracks.tif";
	private static final String BASENAME = "write_";
	private static final int NUM_WRITES = 300;

	private File baseDirectory;

	private final ImgSaver m_saver = new ImgSaver();
	private SCIFIOImgPlus<?> m_sfimg;

	@Before
	public void setup() throws IncompatibleTypeException, ImgIOException {

		// cleanup
		baseDirectory = new File(getClass().getResource("/").getPath(), "../output");
		FileUtils.deleteRecursively(baseDirectory);
		assertTrue(baseDirectory.mkdirs());

		final File testImage = new File(getClass().getResource(IMG1).getPath());
		final ImgOpener opener = new ImgOpener();
		List<SCIFIOImgPlus<?>> imgs = opener.openImgs(testImage.getPath());
		m_sfimg = imgs.get(0);
	}
	
	@After
	public void cleanup() throws Exception {
		FileUtils.deleteRecursively(baseDirectory);
	}

	@Test
	public void tiffNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(new File(baseDirectory, BASENAME + i + ".tif").getPath(), m_sfimg);
		}
	}

	@Test
	public void pngNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(new File(baseDirectory, BASENAME + i + ".png").getPath(), m_sfimg);
		}
	}

	@Test
	public void epsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(new File(baseDirectory, BASENAME + i + ".eps").getPath(), m_sfimg);
		}
	}

	@Test
	public void icsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(new File(baseDirectory, BASENAME + i + ".ics").getPath(), m_sfimg);
		}
	}

	@Test
	public void jpgNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		for (int i = 0; i < NUM_WRITES; i++) {
			m_saver.saveImg(new File(baseDirectory, BASENAME + i + ".jpg").getPath(), m_sfimg);
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
