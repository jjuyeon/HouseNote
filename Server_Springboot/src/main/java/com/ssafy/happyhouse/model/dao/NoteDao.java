package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.happyhouse.model.NoteDto;

public interface NoteDao {
	public int deleteNote(Map<String, Object> map) throws SQLException;
	public NoteDto getNote (Map<String, Object> map) throws SQLException;
	public int addNote(NoteDto note) throws SQLException;
	public int updateNote(NoteDto note) throws SQLException;
}
