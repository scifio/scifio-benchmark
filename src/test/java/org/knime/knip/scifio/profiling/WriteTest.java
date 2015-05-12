/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2015 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.knime.knip.scifio.profiling;

import static org.junit.Assert.assertTrue;
import io.scif.FormatException;
import io.scif.config.SCIFIOConfig;
import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import java.io.File;
import java.util.List;

import net.imglib2.exception.IncompatibleTypeException;

import org.junit.*;
import org.junit.rules.TestRule;
import org.scijava.util.FileUtils;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

@BenchmarkOptions(benchmarkRounds = 200, warmupRounds = 5)
public class WriteTest {

	/** Needed for JUnit-Benchmarks */
	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();

	private static final String IMG1 = "/FakeTracks.tif";
	private static final String BASENAME = "write_";

	private File baseDirectory;

	private final ImgSaver m_saver = new ImgSaver();
	private SCIFIOImgPlus<?> m_sfimg;
	private SCIFIOConfig m_sequentialConfig = new SCIFIOConfig();


	@Before
	public void setup() throws IncompatibleTypeException, ImgIOException {

		// cleanup
		baseDirectory = new File(getClass().getResource("/").getPath(), "../output");
		FileUtils.deleteRecursively(baseDirectory);
		assertTrue(baseDirectory.mkdirs());

		m_sequentialConfig.writerSetSequential(true);

		// Read test image
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
	public void tiffSequentialTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".tif").getPath(), m_sfimg, m_sequentialConfig);
	}


//	TODO: Can't really benchmark these as they complain a lot about  'Expected positive value for PhysicalSizeY; got 0.0'
	@Test
	public void omeTiffSequentialTest() throws Exception{
		m_saver.saveImg(new File(baseDirectory, BASENAME + ".ome.tif").getPath(), m_sfimg, m_sequentialConfig );
	}


	@Test
	public void omeTiffNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".ome.tif").getPath(), m_sfimg);
	}

	@Test
	public void pngNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".png").getPath(), m_sfimg);
	}

	@Test
	public void pngSequentialTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".png").getPath(), m_sfimg, m_sequentialConfig);
	}

	@Test
	public void epsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".eps").getPath(), m_sfimg);
	}

	@Test
	public void epsSequentialTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".eps").getPath(), m_sfimg, m_sequentialConfig);
	}

	@Test
	public void icsNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".ics").getPath(), m_sfimg);
	}

	@Test
	public void icsSequentialTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".ics").getPath(), m_sfimg, m_sequentialConfig);
	}

	@Test
	public void jpgNaiveTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".jpg").getPath(), m_sfimg);
	}

	@Test
	public void jpgSequentialTest() throws ImgIOException,
			IncompatibleTypeException, FormatException {

		m_saver.saveImg(new File(baseDirectory, BASENAME + ".jpg").getPath(), m_sfimg, m_sequentialConfig);
	}
}
