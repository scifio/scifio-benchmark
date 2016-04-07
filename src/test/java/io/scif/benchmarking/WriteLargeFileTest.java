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
package io.scif.benchmarking;

import io.scif.SCIFIOService;
import io.scif.config.SCIFIOConfig;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.scijava.Context;
import org.scijava.app.StatusService;
import org.scijava.util.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = { "-Djmh.stack.detailLine=true",
		"-Djmh.stack.top=20", "-Djmh.stack.period=5" })
@BenchmarkMode({ Mode.AverageTime })
public class WriteLargeFileTest {

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

	@Test
	public void tiffTest() throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(WriteLargeFileTest.class.getSimpleName()).forks(1)
				.result("resultsLarge.txt").resultFormat(ResultFormatType.TEXT)
				.addProfiler(StackProfiler.class).build();
		new Runner(opt).run();
	}

	@Setup(Level.Trial)
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

	@Benchmark
	public void tiffNaiveBenchmark() throws Exception {
		m_saver.saveImg(m_tiffPath, m_sfimg, m_baseConfig);
	}

	@Benchmark
	public void tiffSequentialBenchmark() throws Exception {
		m_saver.saveImg(m_tiffPath, m_sfimg, m_sequentialConfig);
	}

	@Benchmark
	public void ometiffNaiveBenchmark() throws Exception {
		m_saver.saveImg(m_omeTiffPath, m_sfimg, m_baseConfig);
	}

	@Benchmark
	public void ometiffSequentialBenchmark() throws Exception {
		m_saver.saveImg(m_omeTiffPath, m_sfimg, m_sequentialConfig);
	}

	 @Benchmark
	 public void pngNaiveBenchmark() throws Exception {
	 m_saver.saveImg(m_pngPath, m_sfimg, m_baseConfig);
	 }
	
	 @Benchmark
	 public void pngSequentialBenchmark() throws Exception {
	 m_saver.saveImg(m_pngPath, m_sfimg, m_sequentialConfig);
	 }
	
	 @Benchmark
	 public void epsNaiveBenchmark() throws Exception {
	 m_saver.saveImg(m_epsPath, m_sfimg, m_baseConfig);
	 }
	
	 @Benchmark
	 public void epsSequentialBenchmark() throws Exception {
	 m_saver.saveImg(m_epsPath, m_sfimg, m_sequentialConfig);
	 }
	
	@Benchmark
	public void icsNaiveBenchmark() throws Exception {
		m_saver.saveImg(m_icsPath, m_sfimg, m_baseConfig);
	}
	
	 @Benchmark
	 public void icsSequentialBenchmark() throws Exception {
	 m_saver.saveImg(m_icsPath, m_sfimg, m_sequentialConfig);
	 }
	
	 @Benchmark
	 public void jpgNaiveBenchmark() throws Exception {
	 m_saver.saveImg(m_jpgPath, m_sfimg, m_baseConfig);
	 }
	
	 @Benchmark
	 public void jpgSequentialBenchmark() throws Exception {
	 m_saver.saveImg(m_jpgPath, m_sfimg, m_sequentialConfig);
	 }
}
