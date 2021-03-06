package com.ojas.rpo.security.dao.notes;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Notes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public class NotesDaoImpl extends JpaDao<Notes, Long> implements NotesDao {

	public NotesDaoImpl() {
		super(Notes.class);
	}

	@Override
	@Transactional
	public Notes save(Notes entity) {
		return this.getEntityManager().merge(entity);
	}


//	@Override
//	@Transactional
//	public Response getAllNotes(NotesSearch notesSearch) {
//		Response response = new Response();
//
//		if (notesSearch.getPageNum() == null || notesSearch.getPageSize() == null)
//			return new Response(ExceptionMessage.DataIsEmpty, "REgid and pageNum must required ");
//
//		if (notesSearch.getRegistrationId() == null && notesSearch.getRequirementId() == null) {
//			return new Response(ExceptionMessage.DataIsEmpty, "registrationId and requirement id must required");
//		} else {
//			try {
//				// getting All data from DB
//				StringBuilder getAllQuery = new StringBuilder(
//						"Select note.id,note.notetype,note.notedata,note.registrationId,note.requirementId from testing.notes note where note.registrationId=? ");
//				// getting count of records.
//				StringBuilder countQuery = new StringBuilder(
//						"Select count(*) from testing.notes note where note.registrationId=? ");
//
//				if (notesSearch.getRequirementId() != null) {
//					getAllQuery.append(" and note.requirementId=" + notesSearch.getRequirementId());
//					countQuery.append(" and note.requirementId=" + notesSearch.getRequirementId());
//				}
//				if (notesSearch.getNoteType() != null) {
//					getAllQuery.append(" and note.notetype='" + notesSearch.getNoteType() + "'");
//					countQuery.append(" and note.notetype='" + notesSearch.getNoteType() + "'");
//				}
//				// getting the count of All data
//				Query count = this.getEntityManager().createNativeQuery(countQuery.toString());
//				count.setParameter(1, notesSearch.getRegistrationId());
//				Object countResult = count.getSingleResult();
//				Integer totalRecords = Integer.valueOf(countResult.toString());
//				int startingRow = 0;
//				if (notesSearch.getPageNum() == 1)
//					response.setTotalPages(0);
//				else {
//					startingRow = ((totalRecords) / notesSearch.getPageSize());
//				}
//
//				// seting the limit
//				getAllQuery.append(" order by note.id asc limit " + startingRow + "," + notesSearch.getPageSize());
//
//				// setting the values for getAll Query
//				Query getAll = this.getEntityManager().createNativeQuery(getAllQuery.toString());
//				getAll.setParameter(1, notesSearch.getRegistrationId());
//
//				// Executing the Query
//				List<Object[]> allValuesList = getAll.getResultList();
//				List<Notes> notesList = new ArrayList<>();
//				for (Object[] objects : allValuesList) {
//					Notes notes = new Notes();
//					notes.setId(Long.valueOf(objects[0] + ""));
//					notes.setNoteType(objects[1] + "");
//					notes.setNoteData(objects[2] + "");
//					notes.setRegistrationId(Long.valueOf(objects[3] + ""));
//					notes.setRequirementId(Long.valueOf(objects[4] + ""));
//					notesList.add(notes);
//				}
//
	
//				if (notesList != null && !notesList.isEmpty())
//					response = new Response(ExceptionMessage.StatusSuccess, notesList);
//				else
//					return new Response(ExceptionMessage.DataIsEmpty, "No data available");
//
//				Integer totalPages = 1;
//				if (totalRecords == 0) {
//					response.setTotalPages(0);
//				} else {
//					totalPages = totalRecords / notesSearch.getPageSize();
//					totalPages = (totalRecords % notesSearch.getPageSize() > 0) ? totalPages + 1 : totalPages;
//
//				}
// 
//				response.setTotalPages(totalPages);
//				response.setTotalRecords(totalRecords);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new Response(HttpStatus.CONFLICT, "500 ");
//			}
//		}
//
//		return response;
//	}

	@Override
	@Transactional
	public Response deleteById(Integer id) {

		Query query = this.getEntityManager().createNativeQuery("delete from notes where id=" +id);
		if (query.executeUpdate() != 0)
			return new Response(ExceptionMessage.StatusSuccess, "Record deleted");
		else
			return new Response(ExceptionMessage.Bad_Request, "Id not valid");

	}

	@Override
	public Response getAllNotes(NotesSearch notesSearch) {
		// TODO Auto-generated method stub
		return null;
	}

}
