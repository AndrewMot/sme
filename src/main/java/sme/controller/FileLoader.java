package sme.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import sme.exception.NotInputFileException;
import sme.util.Utility;

public class FileLoader {
	private BlockingQueue<String> urls;
	private String inputFile;
	private BufferedReader in;
	private final Logger log = Logger.getLogger(FileLoader.class);
	private boolean initiated;
	
	public FileLoader(String in) {
		this.urls = new LinkedBlockingQueue<>();
		this.inputFile = in;
		this.setInitiated(false);
	}

	public void init() throws NotInputFileException{
		log.info("Opening input File");
		File iF = new File(this.inputFile);
		String message = null;
		try {
			if(this.inputFile.isEmpty() || !iF.exists()) {
				log.warn("Input File was not opened");
				throw new NotInputFileException(Utility.NOT_INPUT_FILE);
			}
			this.in = new BufferedReader(new FileReader(this.inputFile));
			this.setInitiated(true);
			message = String.format("Input File Opened: %s", this.inputFile);
			log.info(message);
		} catch (Exception e) {
			message = String.format("Error while opening input file. Error: %s", e.getMessage());
			log.error(message);
			throw new NotInputFileException(Utility.NOT_INPUT_FILE);
		}
	}
	
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
		
	public BlockingQueue<String> getUrls() {
		return urls;
	}

	public boolean isInitiated() {
		return initiated;
	}

	public void setInitiated(boolean initiated) {
		this.initiated = initiated;
	}	
	
}
