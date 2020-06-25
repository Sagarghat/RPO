package com.ojas.rpo.security.util;

public class QueryGenerationForReports {
	private QueryGenerationForReports() {

	}

	private static final String OFFER = "Offered";
	private static final String NOTRESPONDED = "Not Responded";
	private static final String NOSHOW = "No Shows";
	private static final String INTERVIEW = "Interview";
	private static final String SHORTLISTED = "submission";
	private static final String REQUIREMENTS = "Requirements";

	public static String getQuery(Reports reports) {
		String role = reports.getRole();
		Long userId = reports.getRoleUserId();
		Long regId = reports.getRegistrationId();
		String year = reports.getYear();
		String month = reports.getMonth();
		String viewName = null;
		String day = reports.getDay();
		String quarter = String.valueOf(reports.getQuraterNumber());
		String userRoleType = null;
		StringBuilder rejectedQuery = new StringBuilder("select count(map.id) from testing.candidateMapping map");
		StringBuilder shortlistedQuery = new StringBuilder("select count(map.id) from testing.candidateMapping map");
		String leadQuery = " join testing.user leadUser  ";
		String recruiterQuery = " join testing.user recruiteruser ";

		System.out.println(reports.getSubmissionCondition() + "" + reports.getRejectedCondition());

		RequiredStatuses requiredStatuses = getStatuses(reports.getSubmissionCondition(),
				reports.getRejectedCondition());
		String firstStatusName = requiredStatuses.getFirstStatus();
		String secondStatusName = requiredStatuses.getSecondStatus();
		String firstAssessmentName = requiredStatuses.getFirstAssessment();
		String secondAssessmentName = requiredStatuses.getSecondAssessment();
		String detailsQuery = " join testing.interviewdetails details on details.bdmreq_id=map.bdmreq_id";
		String clientQuery = " join testing.client c on c.leadId=leadUser.id \n";
		String bdmreqQuery = " join testing.bdmreq req on req.client_id=c.id \n";
		String firstStatus = " join testing.candidatestatus shortlistedstatus on shortlistedstatus.status ='"
				+ firstStatusName + "'";
		String secondStatus = "  join testing.candidatestatus rejectedstatus on rejectedstatus.status ='"
				+ secondStatusName + "'";
		String firstAssessment = "inner join testing.assesmentname ass on ass.assesmentName='" + firstAssessmentName
				+ "'";
		String secondAssessment = "inner join testing.assesmentname ass on ass.assesmentName='" + secondAssessmentName
				+ "'";

		if (role.equalsIgnoreCase("SuperAdmin")) {
			viewName = "superAdminView";
			userRoleType = "superuser";
			rejectedQuery.append(" join testing.user " + userRoleType);
			rejectedQuery.append("   join testing.user amuser on amuser.reportingId_id= " + userRoleType + ".id \n "
					+ leadQuery + " on leadUser.reportingId_id=amuser.id \n" + recruiterQuery
					+ " on recruiteruser.reportingId_id=leadUser.id \n" + clientQuery + bdmreqQuery);

			shortlistedQuery.append(" join testing.user " + userRoleType);
			shortlistedQuery.append("   join testing.user amuser on amuser.reportingId_id= " + userRoleType + ".id \n "
					+ leadQuery + " on leadUser.reportingId_id=amuser.id \n" + recruiterQuery
					+ " on recruiteruser.reportingId_id=leadUser.id \n" + clientQuery + bdmreqQuery);
			if (!reports.getRejectedCondition().equalsIgnoreCase("feedback")) {
				rejectedQuery.append(secondStatus + "\n" + secondAssessment + "\n");
			} else {
				rejectedQuery.append(detailsQuery);
			}

			if (!reports.getSubmissionCondition().equalsIgnoreCase(REQUIREMENTS)) {
				shortlistedQuery.append(firstStatus + "\n" + firstAssessment + "\n");
			}

		} else if ((role.equalsIgnoreCase("AM"))) {
			viewName = "AmView";
			userRoleType = "amuser";
			rejectedQuery.append(" join testing.user " + userRoleType);
			rejectedQuery.append("  " + leadQuery + " on leadUser.reportingId_id=" + userRoleType + ".id \n"
					+ recruiterQuery + "on  recruiteruser.reportingId_id=leadUser.id \n " + clientQuery + bdmreqQuery);

			shortlistedQuery.append(" join testing.user " + userRoleType);

			shortlistedQuery.append("  " + leadQuery + " on leadUser.reportingId_id=" + userRoleType + ".id \n"
					+ recruiterQuery + "on  recruiteruser.reportingId_id=leadUser.id \n " + clientQuery + bdmreqQuery);
			if (!reports.getRejectedCondition().equalsIgnoreCase("feedback")) {
				rejectedQuery.append(secondStatus + "\n" + secondAssessment + "\n");
			} else {
				rejectedQuery.append(detailsQuery);
			}
			if (!reports.getSubmissionCondition().equalsIgnoreCase(REQUIREMENTS)) {
				shortlistedQuery.append(firstStatus + "\n" + firstAssessment + "\n");
			}
		} else if (role.equalsIgnoreCase("Lead")) {
			viewName = "leadView";
			userRoleType = "leadUser";
			rejectedQuery.append(" join testing.user " + userRoleType);
			rejectedQuery.append(recruiterQuery + " on  recruiteruser.reportingId_id =" + userRoleType + ".id "
					+ clientQuery + bdmreqQuery);
			shortlistedQuery.append(" join testing.user " + userRoleType);
			shortlistedQuery.append(recruiterQuery + " on  recruiteruser.reportingId_id =" + userRoleType + ".id "
					+ clientQuery + bdmreqQuery);
			if (!reports.getRejectedCondition().equalsIgnoreCase("feedback")) {
				rejectedQuery.append(secondStatus + "\n" + secondAssessment + "\n");
			} else {
				rejectedQuery.append(detailsQuery);
			}
			if (!reports.getSubmissionCondition().equalsIgnoreCase(REQUIREMENTS)) {
				shortlistedQuery.append(firstStatus + "\n" + firstAssessment + "\n");
			}
		}

		else if (role.equalsIgnoreCase("Recruiter")) {
			viewName = "recruiterView";
			userRoleType = "recruiterUser";
			rejectedQuery
					.append(" join testing.user " + userRoleType + " on  " + userRoleType + ".id= map.mappedUser_id");
			shortlistedQuery
					.append(" join testing.user " + userRoleType + " on  " + userRoleType + ".id= map.mappedUser_id");

			shortlistedQuery.append(firstStatus + "\n" + firstAssessment + "\n");
		}

		rejectedQuery.append(" where " + userRoleType + ".role='" + role + "'  and " + userRoleType + ".id=" + userId
				+ " and " + userRoleType + ".reg_id=" + regId + "\n");
		if (!reports.getRejectedCondition().equalsIgnoreCase("feedback")) {

			rejectedQuery.append(" and map.candidateStatus=rejectedstatus.id");
			rejectedQuery.append(" and  map.stageOfInterview=ass.id \n");
		}
		shortlistedQuery.append(" where " + userRoleType + ".role='" + role + "'  and " + userRoleType + ".id=" + userId
				+ " and " + userRoleType + ".reg_id=" + regId + "\n");
		if (!reports.getSubmissionCondition().equalsIgnoreCase(REQUIREMENTS)) {

			shortlistedQuery.append(" and map.candidateStatus=shortlistedstatus.id");
			shortlistedQuery.append(" and  map.stageOfInterview=ass.id \n");
		}
		if (year == null || year.isEmpty())
			year = "year(now())";

		rejectedQuery.append("  and year(map.lastUpdatedDate)=" + year + " \n");
		shortlistedQuery.append(" and year(map.lastUpdatedDate)=" + year + " \n");
		if (month != null && !month.isEmpty()) {
			rejectedQuery.append("  and month(map.lastUpdatedDate)=" + month + "\n");
			shortlistedQuery.append("  and month(map.lastUpdatedDate)=" + month + "\n");
		}
		if (quarter != null && quarter.isEmpty()) {
			rejectedQuery.append("  and quarter(map.lastUpdatedDate)=" + quarter + "\n");
			shortlistedQuery.append("  and quarter(map.lastUpdatedDate)=" + quarter + "\n");
		}

		if (day != null && !day.isEmpty()) {
			rejectedQuery.append("  and day(map.lastUpdatedDate)=" + day + "\n");
			shortlistedQuery.append("  and day(map.lastUpdatedDate)=" + day + "\n");
		}

		rejectedQuery.append(
				" and  map.mappedUser_id=recruiterUser.id  and map.reg_id=" + reports.getRegistrationId() + " \n ");
		shortlistedQuery.append(
				"  and  map.mappedUser_id=recruiterUser.id and map.reg_id=" + reports.getRegistrationId() + " \n");
		if (reports.getRejectedCondition().equalsIgnoreCase("feedback")) {
			rejectedQuery.append(" and details.bdmReq_id=map.bdmreq_id and details.reg_id=" + reports.getRegistrationId()
					+ " and details.status='hold' or details.status is null ");
		}
		if (reports.getSubmissionCondition().equalsIgnoreCase(REQUIREMENTS)) {
			shortlistedQuery.append(" order by map.bdmreq_id");
		}

		return "create or replace view " + viewName + " as select(" + shortlistedQuery.toString() + ") as "
				+ reports.getSubmissionCondition().replace(" ", "") + "count ,(" + rejectedQuery.toString() + ")as "
				+ reports.getRejectedCondition().replace(" ", "") + "Count";

	}

	public static RequiredStatuses getStatuses(String submssionCond, String rejectedCond) {
		String firstStatus = null;
		String secondStatus = null;
		String firstAssessment = null;
		String secondAssessment = null;
		String xHeading = null;
		String yHeading = null;
		if (submssionCond.equalsIgnoreCase("Submissions") && rejectedCond.equalsIgnoreCase("Rejections")) {
			firstStatus = "Shortlisted";
			firstAssessment = SHORTLISTED;
			secondStatus = "Rejected";
			secondAssessment = SHORTLISTED;
			xHeading = "Submissions";
			yHeading = "Rejections";
		}

		if (submssionCond.equalsIgnoreCase("submitted to Customer")
				&& rejectedCond.equalsIgnoreCase("screening rejection")) {
			firstStatus = "Submit to Client";
			firstAssessment = SHORTLISTED;
			secondStatus = "Rejected";
			secondAssessment = "Screening";
			xHeading = "submitted to client";
			yHeading = "screening rejection";
		}

		if (submssionCond.equalsIgnoreCase("selections") && rejectedCond.equalsIgnoreCase(OFFER)) {
			firstStatus = INTERVIEW;
			firstAssessment = "Qualified";
			secondStatus = OFFER;
			secondAssessment = OFFER;
			xHeading = "selections";
			yHeading = OFFER;

		}
		if (submssionCond.equalsIgnoreCase(OFFER) && rejectedCond.equalsIgnoreCase("onboards")) {
			firstStatus = OFFER;
			firstAssessment = OFFER;
			secondStatus = "On Boarded";
			secondAssessment = "Hiring";
			xHeading = OFFER;
			yHeading = "onboards";
		}

		if (submssionCond.equalsIgnoreCase("lead submission") && rejectedCond.equalsIgnoreCase("lead rejection")) {
			firstStatus = "approved";
			firstAssessment = "screening";
			secondStatus = "Rejected";
			secondAssessment = "screening";
			xHeading = "lead submissions";
			yHeading = "lead rejections";
		}
		if (submssionCond.equalsIgnoreCase(OFFER) && rejectedCond.equalsIgnoreCase("BackOuts")) {
			firstStatus = OFFER;
			firstAssessment = OFFER;
			secondStatus = "Back Out";
			secondAssessment = "Others";
			xHeading = OFFER;
			yHeading = "BackOuts";

		}
		if (submssionCond.equalsIgnoreCase(NOSHOW) && rejectedCond.equalsIgnoreCase(NOSHOW)) {
			firstStatus = NOTRESPONDED;
			firstAssessment = INTERVIEW;
			secondStatus = NOTRESPONDED;
			secondAssessment = "Interveiw";

			xHeading = NOSHOW;
			yHeading = NOSHOW;

		}

		if (submssionCond.equalsIgnoreCase(REQUIREMENTS) && rejectedCond.equalsIgnoreCase("Onboards")) {
			secondStatus = "On Boarded";
			secondAssessment = "Hiring";
			xHeading = REQUIREMENTS;
			yHeading = "Onboards";

		}
		if (submssionCond.equalsIgnoreCase("Schedules") && rejectedCond.equalsIgnoreCase(NOSHOW)) {
			firstStatus = "In-Evaluation";
			firstAssessment = INTERVIEW;
			secondStatus = NOTRESPONDED;
			secondAssessment = INTERVIEW;
			xHeading = "Schedules";
			yHeading = NOSHOW;
		}

		if (submssionCond.equalsIgnoreCase(REQUIREMENTS) && rejectedCond.equalsIgnoreCase("feedback")) {

			xHeading = REQUIREMENTS;
			yHeading = "feedback";

		}
		return new RequiredStatuses(firstStatus, secondStatus, firstAssessment, secondAssessment, xHeading, yHeading);

	}

}
