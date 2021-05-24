package com.ssafy.happyhouse.model.service;

import java.util.Map;

import com.ssafy.happyhouse.model.NoteDto;

public interface NoteService {
	public NoteDto getNote(Map<String, Object> map) throws Exception;
	public int updateNote(NoteDto note) throws Exception;
}
