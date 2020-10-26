package com.capg.census;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CensusStateAnalyserTest {

	private StateCensusAnalyser censusAnalyser;

	public static final String STATE_CENSUS_FILE_PATH = "D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensus.csv";
	public static final String STATE_EMPTY_FILE = "D:\\neeraj_workspace\\IndianCensusAnalyser1\\StateCensusData.csv";
	private static final String STATE_CENSUS_WRONG_HEADER_FILE_PATH = "D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensusWrongHeader.csv";
	private static final String STATE_CENSUS_WRONG_DELIMITER_FILE_PATH = "D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensusWrongDelimeter.csv";
	public static final String INDIA_CENSUS_FILE_PATH = "D:\\Capgemini Workspace\\IndiaCensusAnalyser\\IndiaCensusData.csv";

	@Before
	public void initialize() {
		censusAnalyser = new StateCensusAnalyser();
	}
	
	//Statecode Census Test

	@Test
	public void givenStateCensusCSVFile_ShouldReturnNumberOfRecords() throws CensusAnalyserException, CsvException {
		int noOfEntries = censusAnalyser.loadStateCsvData(STATE_CENSUS_FILE_PATH);
		Assert.assertEquals(29, noOfEntries);
	}

	@Test
	public void givenStateCensus_WrongCSVFile_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData("D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensus123.csv");
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void givenStateCensus_WrongType_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData("D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensus.txt");
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_TYPE, e.type);
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void givenStateCensus_WrongHeader_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData(STATE_CENSUS_WRONG_HEADER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INTERNAL_ISSUE, e.type);
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void givenStateCensus_WrongDelimiter_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData(STATE_CENSUS_WRONG_DELIMITER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INTERNAL_ISSUE, e.type);
		} catch (CsvException e) {
			e.printStackTrace();
		}
	}
	
	
	//India Code Census Test
	
	@Test
	public void givenIndiaCensusCSVFile_ShouldReturnNumberOfRecords() throws CensusAnalyserException {
		int noOfEntries = censusAnalyser.loadStateCode(INDIA_CENSUS_FILE_PATH);
		Assert.assertEquals(37, noOfEntries);
	}

	@Test
	public void givenIndiaCensus_WrongCSVFile_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCode("D:\\Capgemini Workspace\\IndiaCensusAnalyser\\indiaCaensusData12.csv");
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void givenIndiaCensus_WrongType_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCode("D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensus.txt");
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_TYPE, e.type);
		}
	}

	@Test
	public void givenIndiaCensus_WrongHeader_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCode(STATE_CENSUS_WRONG_HEADER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INTERNAL_ISSUE, e.type);
		}
	}

	@Test
	public void givenIndiaCensus_WrongDelimiter_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCode(STATE_CENSUS_WRONG_DELIMITER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INTERNAL_ISSUE, e.type);
		}
	}

}