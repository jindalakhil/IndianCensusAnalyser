package com.capg.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	public int loadStateCsvData(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException(".csv file not found", CensusAnalyserException.ExceptionType.WRONG_TYPE);
		}

		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			Iterator<CsvStateCensus> csvStateCensusIterator = this.getCsvFileIterator(reader, CsvStateCensus.class);
			return this.getCount(csvStateCensusIterator );
			
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.INTERNAL_ISSUE);
		}
	}
	
	public int loadStateCode(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException(".csv file not found", CensusAnalyserException.ExceptionType.WRONG_TYPE);
		}
		
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			Iterator<StateCodeCSV> csvStateCensusIterator = this.getCsvFileIterator(reader, StateCodeCSV.class);
			return this.getCount(csvStateCensusIterator);
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException("Data not according to format",
					CensusAnalyserException.ExceptionType.INTERNAL_ISSUE);
		}
	}
	
	private<E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException{
		try {
			CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			CsvToBean<E> csvToBean = csvToBeanBuilder.withType(csvClass)
					                             .withIgnoreLeadingWhiteSpace(true).build();
			return csvToBean.iterator();
		}catch(IllegalStateException e){
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
	
	private<E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int)StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}

}
