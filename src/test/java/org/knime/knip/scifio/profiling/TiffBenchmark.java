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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.scijava.util.FileUtils;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

@BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 5)
public class TiffBenchmark {

	/** Needed for JUnit-Benchmarks */
	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();

	private static final String IMG1 = "/FakeTracks.tif";
	private static final String BASENAME = "write_";

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

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".tif").getPath(), m_sfimg);
	}

	@Test
	public void pngNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".png").getPath(), m_sfimg);
	}

	@Test
	public void epsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".eps").getPath(), m_sfimg);
	}

	@Test
	public void icsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".ics").getPath(), m_sfimg);
	}

	@Test
	public void jpgNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".jpg").getPath(), m_sfimg);
	}

//	@Test
//	public void jp2NaiveTest() throws ImgIOException,
//			IncompatibleTypeException, FormatException {
//
//		m_saver.saveImg(BASEFOLDER + BASENAME + ".jp2", m_sfimg);
//	}

}
