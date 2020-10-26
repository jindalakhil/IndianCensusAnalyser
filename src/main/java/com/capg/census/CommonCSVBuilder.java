package com.capg.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class CommonCSVBuilder<E> implements ICsvBuilder {

	@Override
	public<E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CsvException {
		try {
			CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeaderComments(csvClass));
			return (Iterator<E>) csvParser.iterator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return null;
	}
}