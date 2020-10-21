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

	@Before
	public void initialize() {
		censusAnalyser = new StateCensusAnalyser();
	}

	@Test
	public void givenStateCensusCSVFile_ShouldReturnNumberOfRecords() throws CensusAnalyserException {
		int noOfEntries = censusAnalyser.loadStateCsvData(STATE_CENSUS_FILE_PATH);
		Assert.assertEquals(29, noOfEntries);
	}

	@Test
	public void givenStateCensus_WrongCSVFile_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData("D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensus.csv");
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_CSV, e.type);
		}
	}

	@Test
	public void givenStateCensus_WrongType_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData("D:\\Capgemini Workspace\\IndiaCensusAnalyser\\stateCensus.txt");
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_TYPE, e.type);
		}
	}

	@Test
	public void givenStateCensus_WrongHeader_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData(STATE_CENSUS_WRONG_HEADER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INTERNAL_ISSUE, e.type);
		}
	}

	@Test
	public void givenStateCensus_WrongDelimiter_ShouldThrowException() {
		try {
			censusAnalyser.loadStateCsvData(STATE_CENSUS_WRONG_DELIMITER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INTERNAL_ISSUE, e.type);
		}
	}

}