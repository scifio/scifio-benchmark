package io.scif.benchmarking;

import io.scif.SCIFIOService;
import io.scif.config.SCIFIOConfig;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import io.scif.img.SCIFIOImgPlus;

import org.openjdk.jmh.annotations.*;
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
@Warmup(iterations = 10)
@Measurement(iterations = 50, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode({ Mode.Throughput})
public class WriteSmallFileTest {

	private static final String IMG1 = "/FakeTracks.tif";
	
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

//	@Test
	public void tiffTest() throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(WriteSmallFileTest.class.getSimpleName()).forks(1)
				.result("results-small.txt").resultFormat(ResultFormatType.TEXT)
				.build();
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
