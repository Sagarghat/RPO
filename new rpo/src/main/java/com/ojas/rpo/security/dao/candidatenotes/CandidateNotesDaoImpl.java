package com.ojas.rpo.security.dao.candidatenotes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CandidateNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

@Repository
public class CandidateNotesDaoImpl extends JpaDao<CandidateNotes, Long> implements CandidateNotesDao {

	public CandidateNotesDaoImpl() {
		super(CandidateNotes.class);

	}

	@Override
	@Transactional
	public CandidateNotes save(CandidateNotes entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional
	public Response deleteById(Long id) {
		String deleteQuery = "delete from candidatenote where id=" + id;
		Query delete = this.getEntityManager().createNativeQuery(deleteQuery);

		if (delete.executeUpdate() != 0)
			return new Response(ExceptionMessage.StatusSuccess, "Record deleted with id: " + id);
		else
			return new Response(ExceptionMessage.Bad_Request, "Id not valid");

	}

	@Override
	@Transactional
	public Response getAllContactNotes(NotesSearch notesSearch) {
		Response response = new Response();

		if (notesSearch.getPageNum() == null || notesSearch.getPageSize() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "REgid and pageNum must required ");

		if (notesSearch.getRegistrationId() == null && notesSearch.getResourceId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "registrationId and interview id must required");
		} else {
			try {
				// getting All data from DB
				StringBuilder getAllQuery = new StringBuilder(
						"Select note.id,note.notetype,note.notedata,note.registrationId,note.candidateId from testing.`candidatenote` note where note.registrationId=? ");
				// getting count of records.
				StringBuilder countQuery = new StringBuilder(
						"Select count(*) from testing.`candidatenote` note where note.registrationId=? ");

				if (notesSearch.getResourceId() != null) {
					getAllQuery.append(" and note.candidateId=" + notesSearch.getResourceId());
					countQuery.append(" and note.candidateId=" + notesSearch.getResourceId());
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
				List<CandidateNotes> candidateNotesList = new ArrayList<>();
				// Select
				// note.id,note.notetype,note.notedata,note.registrationId,note.interviewId
				for (Object[] objects : allValuesList) {
					CandidateNotes candidateNotes = new CandidateNotes();
					if (objects[0] != null)
						candidateNotes.setId(Long.valueOf(objects[0] + ""));
					if (objects[1] != null)
						candidateNotes.setNoteType(objects[1] + "");
					if (objects[2] != null)
						candidateNotes.setNoteData(objects[2] + "");
					if (objects[3] != null)
						candidateNotes.setRegistrationId(Long.valueOf(objects[3] + ""));
					if (objects[4] != null)
						candidateNotes.setCandidateId(Long.valueOf(objects[4] + ""));
					candidateNotesList.add(candidateNotes);
				}

				if (candidateNotesList != null && !candidateNotesList.isEmpty())
					response = new Response(ExceptionMessage.StatusSuccess, candidateNotesList);
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
