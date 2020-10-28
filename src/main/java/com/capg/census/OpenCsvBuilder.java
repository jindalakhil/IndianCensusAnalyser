package com.capg.census;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvBuilder implements ICsvBuilder {

	public<E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CsvException{
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.build();
			return csvToBean.iterator();
		}catch(IllegalStateException e){
			throw new CsvException(e.getMessage(),CsvException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
	
	@Override
	public <E> List<E> getCSVFileList(Reader reader, Class<E> csvClass) throws CsvException {
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(csvClass);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<E> csvToBean = csvToBeanBuilder.build();
			return csvToBean.parse();
		} catch (IllegalStateException e) {
			throw new CsvException("File data not proper", CsvException.ExceptionType.UNABLE_TO_PARSE);
		}

	}
}