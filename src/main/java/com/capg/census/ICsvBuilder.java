package com.capg.census;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICsvBuilder {
	public <E> List<E> getCSVFileList(Reader reader, Class<E> csvClass) throws CsvException;
	public <E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CsvException;
}
