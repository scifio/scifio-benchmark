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
