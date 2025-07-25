package com.dats.exceptions;

public class DuplicateEntryException extends Exception{
	 public DuplicateEntryException(String errorMessage, Throwable err) {
		 super(errorMessage, err);
	 }

}
