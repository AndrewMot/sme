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
import sme.util.Utility;

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
		String file = String.format("%s.%s", fileName, Utility.FILE_EXTENSION);
		URL url = Thread.currentThread().getContextClassLoader().getResource(file);
		FileLoader fl = new FileLoader(url.getPath());
		fl.init();
		assertTrue("FileLoader Initiated", fl.isInitiated());
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#init()}.
	 */
	@Test
	public void throwsNotInputFileFileNotExist() throws IOException, NotInputFileException {
		String archivo = String.format("%s.%s", Utility.DEFAULT_INPUT_FILE, Utility.FILE_EXTENSION);
		FileLoader fl = new FileLoader(archivo);
		exception.expect(NotInputFileException.class);
		exception.expectMessage(Utility.NOT_INPUT_FILE);
		fl.init();
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#init()}.
	 */
	@Test
	public void throwsNotInputFileEmptyFileName() throws NotInputFileException {
		FileLoader fl = new FileLoader("");
		exception.expect(NotInputFileException.class);
		exception.expectMessage(Utility.NOT_INPUT_FILE);
		fl.init();
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#loadFile()}.
	 */
	@Test
	public void testLoadFileNotInitiated() {
		String archivo = String.format("%s.%s", Utility.DEFAULT_INPUT_FILE, Utility.FILE_EXTENSION);
		FileLoader fl = new FileLoader(archivo);
		fl.loadFile();
		assertFalse("File Loader not initiated", fl.isInitiated());
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#loadFile()}.
	 */
	@Test
	public void testLoadFileEmptyList() throws IOException, NotInputFileException {
		tmp.create();
		String file = String.format("%s.%s", Utility.DEFAULT_INPUT_FILE, Utility.FILE_EXTENSION);
		FileLoader fl = new FileLoader(tmp.newFile(file).getAbsolutePath());
		fl.init();
		fl.loadFile();
		assertEquals("Empty URL List", 0, fl.getUrls().size());
	}

	/**
	 * Test method for {@link sme.controller.FileLoader#loadFile()}.
	 */
	@Test
	public void testLoadFile() throws IOException, InterruptedException, NotInputFileException {
		String fileName = "TestInputFile";
		String file = String.format("%s.%s", fileName, Utility.FILE_EXTENSION);
		URL url = Thread.currentThread().getContextClassLoader().getResource(file);
		FileLoader fl = new FileLoader(url.getPath());
		fl.init();
		fl.loadFile();
		String[] t = { "http://www.extremetech.com/", "http://www.technewsworld.com/" };
		assertArrayEquals("Same Url List", fl.getUrls().toArray(), t);
	}

}
