package com.capg.census;

public class CsvBuilderFactory {
public static ICsvBuilder createCsvBuilder() {
	return new OpenCsvBuilder();
}
}