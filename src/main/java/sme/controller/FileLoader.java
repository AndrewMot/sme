package sme.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import sme.exception.NotInputFileException;
import sme.util.Constants;
/**
 * Class for handle the input file with list of URLs
 * @author Andrés Motavita
 *
 */
public class FileLoader {
	/**
	 * Queue with URLs loaded from input file.
	 */
	private BlockingQueue<String> urls;
	/**
	 * Input file path
	 */
	private String inputFile;
	/**
	 * Reader
	 */
	private BufferedReader in;
	/**
	 * Logger
	 */
	private final Logger log = Logger.getLogger(FileLoader.class);
	/**
	 * Flag
	 */
	private boolean initiated;
	/**
	 * Constructor
	 * @param in
	 */
	public FileLoader(String in) {
		this.urls = new LinkedBlockingQueue<>();
		this.inputFile = in;
		this.setInitiated(false);
	}
	/**
	 * Method to init the load.
	 * @throws NotInputFileException
	 */
	public void init() throws NotInputFileException{
		log.info("Opening input File");
		File iF = new File(this.inputFile);
		String message = null;
		try {
			if(this.inputFile.isEmpty() || !iF.exists()) {
				log.warn("Input File was not opened");
				throw new NotInputFileException(Constants.NOT_INPUT_FILE);
			}
			this.in = new BufferedReader(new FileReader(this.inputFile));
			this.setInitiated(true);
			message = String.format("Input File Opened: %s", this.inputFile);
			log.info(message);
		} catch (Exception e) {
			message = String.format("Error while opening input file. Error: %s", e.getMessage());
			log.error(message);
			throw new NotInputFileException(Constants.NOT_INPUT_FILE);
		}
	}
	/**
	 * Load the URL list to a queue
	 */
	public void loadFile() {
		if(!isInitiated()) {
			log.warn("Input File is not ready.");
			return;
		}
		try {
			log.info("Loading Input File.");
			String line = null;
			while((line = in.readLine()) != null) {
				if(!line.isEmpty())
					this.urls.offer(line.trim());
			}
			log.info("Load finished.");
		} catch (Exception e) {
			String mensaje = String.format("There was an error while loading urls. Error: %s", e.getMessage());
			log.error(mensaje);
		}
	}
	/**
	 * 
	 * @return URLs
	 */
	public BlockingQueue<String> getUrls() {
		return urls;
	}
	/**
	 * 
	 * @return get state of flag
	 */
	public boolean isInitiated() {
		return initiated;
	}
	/**
	 * set the flag
	 * @param initiated
	 */
	public void setInitiated(boolean initiated) {
		this.initiated = initiated;
	}	
	
}
