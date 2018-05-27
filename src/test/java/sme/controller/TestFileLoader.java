package sme.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import sme.exception.NotInputFileException;
import sme.util.Constants;

public class TestFileLoader {
	/**
	 * Mock for filesystem
	 */
	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();
	/**
	 * Mock for exceptions
	 */
	@Rule
	public ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link sme.controller.FileLoader#init()}.
	 */
	@Test
	public void testInit() throws IOException, NotInputFileException {
		String fileName = "TestInputFile";
		String file = String.format("%s.%s", fileName, Constants.FILE_EXTENSION);
		URL url = Thread.currentThread().getContextClassLoader().getResource(file);
		FileLoader fileLoader = new FileLoader(url.getPath());
		fileLoader.init();
		assertTrue("FileLoader Initiated", fileLoader.isInitiated());
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#init()}.
	 */
	@Test
	public void throwsNotInputFileFileNotExist() throws IOException, NotInputFileException {
		String file = String.format("%s.%s", Constants.DEFAULT_INPUT_FILE, Constants.FILE_EXTENSION);
		FileLoader fileLoader = new FileLoader(file);
		exception.expect(NotInputFileException.class);
		exception.expectMessage(Constants.NOT_INPUT_FILE);
		fileLoader.init();
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#init()}.
	 */
	@Test
	public void throwsNotInputFileEmptyFileName() throws NotInputFileException {
		FileLoader fileLoader = new FileLoader("");
		exception.expect(NotInputFileException.class);
		exception.expectMessage(Constants.NOT_INPUT_FILE);
		fileLoader.init();
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#loadFile()}.
	 */
	@Test
	public void testLoadFileNotInitiated() {
		String file = String.format("%s.%s", Constants.DEFAULT_INPUT_FILE, Constants.FILE_EXTENSION);
		FileLoader fileLoader = new FileLoader(file);
		fileLoader.loadFile();
		assertFalse("File Loader not initiated", fileLoader.isInitiated());
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#loadFile()}.
	 */
	@Test
	public void testLoadFileEmptyList() throws IOException, NotInputFileException {
		tmp.create();
		String file = String.format("%s.%s", Constants.DEFAULT_INPUT_FILE, Constants.FILE_EXTENSION);
		FileLoader fileLoader = new FileLoader(tmp.newFile(file).getAbsolutePath());
		fileLoader.init();
		fileLoader.loadFile();
		assertEquals("Empty URL List", 0, fileLoader.getUrls().size());
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#loadFile()}.
	 */
	@Test
	public void testLoadFile() throws IOException, InterruptedException, NotInputFileException {
		String fileName = "TestInputFile";
		String file = String.format("%s.%s", fileName, Constants.FILE_EXTENSION);
		URL url = Thread.currentThread().getContextClassLoader().getResource(file);
		FileLoader fileLoader = new FileLoader(url.getPath());
		fileLoader.init();
		fileLoader.loadFile();
		String[] expectedResult = { "http://www.extremetech.com/", "http://www.technewsworld.com/" };
		assertArrayEquals("Same Url List", fileLoader.getUrls().toArray(), expectedResult);
	}

}
