package com.ed.exception;

import org.hibernate.query.named.NamedObjectRepository;

public class NoRecordFoundException extends RuntimeException {
	
	NoRecordFoundException(){}
	
	NoRecordFoundException(String msg){
		super(msg);
	}
}
