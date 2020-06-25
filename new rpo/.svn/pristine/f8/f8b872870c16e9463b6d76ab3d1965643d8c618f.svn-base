package com.ojas.rpo.security.dao.interviewnotes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public class InterviewNotesDaoImpl extends JpaDao<InterviewNotes, Long> implements InterviewNotesDao {

	public InterviewNotesDaoImpl() {
		super(InterviewNotes.class);
	}

	@Override
	@Transactional
	public InterviewNotes save(InterviewNotes entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional
	public Response deleteById(Long id) {

		String deleteQuery = "delete from interviewnotes where id=" + id;
		Query delete = this.getEntityManager().createNativeQuery(deleteQuery);

		if (delete.executeUpdate() != 0)
			return new Response(ExceptionMessage.StatusSuccess, "Record deleted with id: " + id);
		else
			return new Response(ExceptionMessage.Bad_Request, "Id not valid");
	}

	@Override
	@Transactional
	public Response getAllInterviews(NotesSearch notesSearch) {
		Response response = new Response();

		if (notesSearch.getPageNum() == null || notesSearch.getPageSize() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "REgid and pageNum must required ");

		if (notesSearch.getRegistrationId() == null && notesSearch.getResourceId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "registrationId and interview id must required");
		} else {
			try {
				// getting All data from DB
				StringBuilder getAllQuery = new StringBuilder(
						"Select note.id,note.notetype,note.notedata,note.registrationId,note.interviewId from testing.`interviewnotes` note where note.registrationId=? ");
				// getting count of records.
				StringBuilder countQuery = new StringBuilder(
						"Select count(*) from testing.`interviewnotes` note where note.registrationId=? ");

				if (notesSearch.getResourceId() != null) {
					getAllQuery.append(" and note.interviewId=" + notesSearch.getResourceId());
					countQuery.append(" and note.interviewId=" + notesSearch.getResourceId());
				}
				if (notesSearch.getNoteType() != null) {
					getAllQuery.append(" and note.notetype='" + notesSearch.getNoteType() + "'");
					countQuery.append(" and note.notetype='" + notesSearch.getNoteType() + "'");
				}
				// getting the count of All data
				Query count = this.getEntityManager().createNativeQuery(countQuery.toString());
				count.setParameter(1, notesSearch.getRegistrationId());
				Object countResult = count.getSingleResult();
				Integer totalRecords = Integer.valueOf(countResult.toString());
				int startingRow = 0;
				if (notesSearch.getPageNum() == 1)
					response.setTotalPages(0);
				else {
					startingRow = ((notesSearch.getPageNum() - 1) * notesSearch.getPageSize());
				}

				// seting the limit
				getAllQuery.append(" order by note.id asc limit " + startingRow + "," + notesSearch.getPageSize());

				// setting the values for getAll Query
				Query getAll = this.getEntityManager().createNativeQuery(getAllQuery.toString());
				getAll.setParameter(1, notesSearch.getRegistrationId());

				// Executing the Query
				List<Object[]> allValuesList = getAll.getResultList();
				List<InterviewNotes> interviewNotesList = new ArrayList<>();
				// Select
				// note.id,note.notetype,note.notedata,note.registrationId,note.interviewId
				for (Object[] objects : allValuesList) {
					InterviewNotes interviewNotes = new InterviewNotes();
					if (objects[0] != null)
						interviewNotes.setId(Long.valueOf(objects[0] + ""));
					if (objects[1] != null)
						interviewNotes.setNoteType(objects[1] + "");
					if (objects[2] != null)
						interviewNotes.setNoteData(objects[2] + "");
					if (objects[3] != null)
						interviewNotes.setRegistrationId(Long.valueOf(objects[3] + ""));
					if (objects[4] != null)
						interviewNotes.setInterviewId(Long.valueOf(objects[4] + ""));
					interviewNotesList.add(interviewNotes);
				}

				if (interviewNotesList != null && !interviewNotesList.isEmpty())
					response = new Response(ExceptionMessage.StatusSuccess, interviewNotesList);
				else
					return new Response(ExceptionMessage.DataIsEmpty, "No data available");

				Integer totalPages = 1;
				if (totalRecords == 0) {
					response.setTotalPages(0);
				} else {
					totalPages = totalRecords / notesSearch.getPageSize();
					totalPages = (totalRecords % notesSearch.getPageSize() > 0) ? totalPages + 1 : totalPages;

				}

				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);
			} catch (Exception e) {
				e.printStackTrace();
				return new Response(HttpStatus.CONFLICT, "500 ");
			}
		}

		return response;
	}

}
