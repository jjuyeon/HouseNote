package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.NoteDto;
import com.ssafy.happyhouse.model.RoomDto;
import com.ssafy.happyhouse.model.dao.NoteDao;
import com.ssafy.happyhouse.model.dao.RoomDao;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private SqlSession sqlSession;

	// 조회
	@Override
	public NoteDto getNote(Map<String, Object> map) throws Exception {
		NoteDto note = sqlSession.getMapper(NoteDao.class).getNote(map);
		List<RoomDto> roomInfoList = sqlSession.getMapper(RoomDao.class).listRoom(note.getIdx());
		note.setRoomList(roomInfoList);
		return note;
	}

	// 업데이트
	@Override
	@Transactional
	public int updateNote(NoteDto note) throws Exception {
		int result = 0;
		// room 업데이트
		for(RoomDto room : note.getRoomList()) {
			room.setNoteIdx(note.getIdx());
			if(room.getStatus() == 5) { // 삭제된 방 정보
				result += sqlSession.getMapper(RoomDao.class).deleteRoom(room.getIdx());
			}
			else if(room.getIdx() == 0) { // 새로 생성되는 방 정보
				result += sqlSession.getMapper(RoomDao.class).addRoom(room);
			}else { // 업데이트되는 방 정보
				result += sqlSession.getMapper(RoomDao.class).updateRoom(room);
			}
		}
		// note 업데이트
		result += sqlSession.getMapper(NoteDao.class).updateNote(note);
		return result;
	}
}
