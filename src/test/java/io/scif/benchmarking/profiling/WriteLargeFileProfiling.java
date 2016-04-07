/*
 * #%L
 * SCIFIO library for reading and converting scientific file formats.
 * %%
 * Copyright (C) 2011 - 2016 Board of Regents of the University of
 * Wisconsin-Madison
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
package io.scif.benchmarking.profiling;

import io.scif.SCIFIOService;
import io.scif.config.SCIFIOConfig;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import org.scijava.Context;
import org.scijava.app.StatusService;
import org.scijava.util.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class WriteLargeFileProfiling {

	private static final String IMG1 = "/superLargeTestFile.ome.tif";

	private static final String BASENAME = "write_";

	private File baseDirectory;

	private final Context context = new Context(SCIFIOService.class,
			StatusService.class);
	private final ImgSaver m_saver = new ImgSaver(context);
	private final ImgOpener opener = new ImgOpener(context);
	private SCIFIOImgPlus<?> m_sfimg;

	private final SCIFIOConfig m_baseConfig = new SCIFIOConfig();
	private final SCIFIOConfig m_sequentialConfig = new SCIFIOConfig()
			.writerSetSequential(true);

	private String m_tiffPath;
	private String m_omeTiffPath;
	private String m_pngPath;
	private String m_epsPath;
	private String m_icsPath;
	private String m_jpgPath;


	@Before
	public void setup() throws Exception {
		// cleanup
		baseDirectory = new File("output").getAbsoluteFile();
		FileUtils.deleteRecursively(baseDirectory);
		baseDirectory.mkdirs();

		// Read test image
		final File testImage = new File(getClass().getResource(IMG1).getPath());
		m_sfimg = opener.openImgs(testImage.getPath()).get(0);

		m_tiffPath = getFormatPath("tif");
		m_omeTiffPath = getFormatPath("ome.tif");
		m_pngPath = getFormatPath("png");
		m_epsPath = getFormatPath("eps");
		m_icsPath = getFormatPath("ics");
		m_jpgPath = getFormatPath("jpg");
	}
	
	private String getFormatPath(String fileEnding) {
		return new File(baseDirectory, BASENAME + "." + fileEnding).getPath();
	}

	public void tiffNaiveBenchmark() throws Exception {
		m_saver.saveImg(m_tiffPath, m_sfimg, m_baseConfig);
	}
	
	@Test
	public void tiffSequentialBenchmark() throws Exception {
		m_saver.saveImg(m_tiffPath, m_sfimg, m_sequentialConfig);
	}

	public void ometiffNaiveBenchmark() throws Exception {
		m_saver.saveImg(m_omeTiffPath, m_sfimg, m_baseConfig);
	}
	
	@Test
	public void ometiffSequentialBenchmark() throws Exception {
		m_saver.saveImg(m_omeTiffPath, m_sfimg, m_sequentialConfig);
	}
}
