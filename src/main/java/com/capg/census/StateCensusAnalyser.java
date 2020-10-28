package com.capg.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	List<CsvStateCensus> csvCensusList;
	List<StateCodeCSV> csvStateList;

	public StateCensusAnalyser() {
		this.csvCensusList = new ArrayList<CsvStateCensus>();
		this.csvStateList = new ArrayList<StateCodeCSV>();

	}
	
	public int loadStateCsvData(String csvFilePath) throws CensusAnalyserException, CsvException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException(".csv file not found", CensusAnalyserException.ExceptionType.WRONG_TYPE);
		}

		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
			csvCensusList = csvBuilder.getCSVFileList(reader, CsvStateCensus.class);
			return csvCensusList.size();
			
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.INTERNAL_ISSUE);
		} catch (CsvException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
	
	public int loadStateCode(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException(".csv file not found", CensusAnalyserException.ExceptionType.WRONG_TYPE);
		}
		
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICsvBuilder csvBuilder = CsvBuilderFactory.createCsvBuilder();
			csvStateList = csvBuilder.getCSVFileList(reader, StateCodeCSV.class);
			return csvCensusList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (IllegalStateException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException("Data not according to format",
					CensusAnalyserException.ExceptionType.INTERNAL_ISSUE);
		} catch (CsvException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
	
	private<E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int)StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
	
	public String getSortedCensuByState() throws CensusAnalyserException {
		if (csvCensusList == null || csvCensusList.size() == 0)

		{
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
		Comparator<CsvStateCensus> censusComparator = Comparator.comparing(census -> census.state);

		this.sort(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvCensusList);
		return sortedStateCensus;
	}

	private void sort(Comparator<CsvStateCensus> censusComparator) {
		for (int i = 0; i < csvCensusList.size() - 1; i++) {
			for (int j = 0; j < csvCensusList.size() - 1 - i; j++) {
				CsvStateCensus census1 = csvCensusList.get(j);
				CsvStateCensus census2 = csvCensusList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					csvCensusList.set(j, census2);
					csvCensusList.set(j + 1, census1);
				}
			}
		}
	}

	public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
		if (csvStateList == null || csvStateList.size() == 0) {
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.WRONG_CSV);
		}
		Collections.sort(csvStateList, Comparator.comparing(code -> code.stateCode));
		return new Gson().toJson(csvStateList);
	}
	public String getStatePopulationWiseSortedCensusData() throws CensusAnalyserException {
		if(csvCensusList == null || csvCensusList.size() == 0) {
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.WRONG_CSV);
		}
		Collections.sort(csvCensusList, Comparator.comparing(census -> census.getPopulationData()));
		System.out.println(csvCensusList);
		return new Gson().toJson(csvCensusList);
	}

}
