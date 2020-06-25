package com.ojas.rpo.security.transfer;

import java.util.Date;

public class CandidateJobRatingSearchDTO {
	private Long id;
	private Long rating;
	private String comments;
	private String job;
	private Long candidateId;
	private Date lastUpdateDate;

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "CandidateJobRatingSearchDTO [id=" + id + ", rating=" + rating + ", comments=" + comments + ", job="
				+ job + "]";
	}

}
