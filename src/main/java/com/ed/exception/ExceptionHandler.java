package com.ed.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ed.constants.AppConstants;

@RestControllerAdvice
public class ExceptionHandler {
	
	public ResponseEntity<ExceptionBean> noRecordFound(NoRecordFoundException e){
		
		ExceptionBean exception = new ExceptionBean();
		
		exception.setCode(AppConstants.NO_RECORD_CODE);
		exception.setMsg(e.getMessage());
		
		return new ResponseEntity<ExceptionBean>(exception,HttpStatus.OK);
	}
}
