// 
// Decompiled by Procyon v0.5.36
// 

package com.ojas.rpo.security.dao.candidateReqMapping;

import com.ojas.rpo.security.util.MappingDetails;
import java.nio.file.Path;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.SimpleDateFormat;
import com.ojas.rpo.security.util.MappingStatuses;
import com.ojas.rpo.security.entity.CandidateMappingRequest;
import java.util.Set;
import com.ojas.rpo.security.util.RequirementDetails;
import com.ojas.rpo.security.util.CandidateDetails;
import com.ojas.rpo.security.entity.Skill;
import java.util.HashSet;
import com.ojas.rpo.security.entity.CandidateRequirementMatcher;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;

import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.BdmReqSearch;

import java.util.Iterator;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.CandidateData;
import java.util.ArrayList;
import com.ojas.rpo.security.entity.Response;
import org.springframework.beans.factory.annotation.Value;
import com.ojas.rpo.security.dao.CandidateStatusMaster.CandidateStatusMasterDaoInf;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.dao.JpaDao;

public class JpaCandidateReqMappingDao extends JpaDao<CandidateMapping, Long> implements CandidateReqMappingDao
{
    @Autowired
    private BdmReqDao bdmReqDao;
    @Autowired
    private CandidatelistDao candidateDao;
    @Autowired
    CandidateStatusMasterDaoInf candidateStatusMasterDaoInf;
    @Value("${excelFile}")
    private String excelFilePath;
    
    public JpaCandidateReqMappingDao() {
        super((Class)CandidateMapping.class);
    }
    
    public Response searchCandidatesList( String role,  Long id,  String searchInput, String searchField,  Integer pageNo, Integer pageSize) {
         List<CandidateData> reqList = new ArrayList<CandidateData>();
        List<Object[]> candidatesList = null;
        Query query = null;
        Integer totalRecords = 0;
        Response response = null;
         String sql = "SELECT candmap.candidate_id AS candidateid, candmap.bdmReq_id AS bdmReqId, c.clientName AS clientName, can.submissionDate AS candidateSubmitionDate,can.candidateStatus AS candidateStatus, CONCAT(can.firstName,' ',can.lastName) AS candidateName, req.nameOfRequirement AS nameOfTheReq,u.name AS nameOftheRecruiter, can.offereStatus AS offerStatus  ,candmap.candidate_idFROM `candidatemapping` candmap, `bdmreq` req, `client` c, `candidate` can, `user` u WHERE candmap.bdmReq_id=req.id AND req.client_id=c.id AND candmap.candidate_id=can.id AND u.id=can.user_id AND ";
         StringBuilder sqlBuilder = new StringBuilder(sql);
        String countQuery = "SELECT COUNT(*) FROM `candidatemapping` candmap, `bdmreq` req, `client` c, `candidate` can, `user` u WHERE candmap.bdmReq_id=req.id AND req.client_id=c.id AND candmap.candidate_id=can.id AND u.id=can.user_id AND ";
        if (searchField.equalsIgnoreCase("bdmReqId") || "bdmReqId".contains(searchField)) {
            searchField = "candmap.bdmReq_id";
        }
        else if (searchField.equalsIgnoreCase("clientName") || "clientName".contains(searchField)) {
            searchField = "c.clientName";
        }
        else if (searchField.equalsIgnoreCase("candidateSubmitionDate") || "candidateSubmitionDate".contains(searchField)) {
            searchField = "can.submissionDate";
        }
        else if (searchField.equalsIgnoreCase("candidateStatus") || "candidateStatus".contains(searchField)) {
            searchField = "can.candidateStatus";
        }
        else if (searchField.equalsIgnoreCase("candidateName") || "candidateName".contains(searchField)) {
            searchField = "CONCAT(can.firstName,' ',can.lastName)";
        }
        else if (searchField.equalsIgnoreCase("nameOfTheReq") || "nameOfRequirement".contains(searchField)) {
            searchField = "req.nameOfRequirement";
        }
        else if (searchField.equalsIgnoreCase("nameOftheRecruiter") || "nameOftheRecruiter".contains(searchField)) {
            searchField = "u.name";
        }
        else if (searchField.equalsIgnoreCase("offerStatus") || "offerStatus".contains(searchField)) {
            searchField = "can.offereStatus";
        }
        int startingRow = 0;
        if (pageNo == 1) {
            startingRow = 0;
        }
        else {
            startingRow = (pageNo - 1) * pageSize;
        }
        try {
            if (id != null && role != null) {
                if (role.equalsIgnoreCase("BDM")) {
                    sqlBuilder.append(" (c.primaryContact_id=? OR c.secondaryContact_id=?) AND " + searchField + " LIKE '%" + searchInput + "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
                    query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
                    query.setParameter(1, (Object)id);
                    query.setParameter(2, (Object)id);
                }
                else if (role.equalsIgnoreCase("AM")) {
                    sqlBuilder.append(" c.accountManger_id = ? AND " + searchField + " LIKE '%" + searchInput + "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
                    query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
                    query.setParameter(1, (Object)id);
                    countQuery = String.valueOf(countQuery) + " AND c.accountManger_id = ?";
                    final Query countQ = this.getEntityManager().createNativeQuery(countQuery);
                    countQ.setParameter(1, (Object)id);
                }
                else if (role.equalsIgnoreCase("RECRUITER")) {
                    sqlBuilder.append(" can.user_id=? AND " + searchField + " LIKE '%" + searchInput + "%' ORDER BY c.id DESC LIMIT " + startingRow + "," + pageSize);
                    query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
                    query.setParameter(1, (Object)id);
                    countQuery = String.valueOf(countQuery) + " AND can.user_id = ?";
                    final Query countQ = this.getEntityManager().createNativeQuery(countQuery);
                    countQ.setParameter(1, (Object)id);
                }
                else if (role.equalsIgnoreCase("lead")) {
                    this.getEntityManager().createNativeQuery(countQuery);
                }
            }
            else {
                sqlBuilder.append(String.valueOf(searchField) + " LIKE '%" + searchInput + "%' ORDER BY req.id DESC LIMIT " + startingRow + "," + pageSize);
                query = this.getEntityManager().createNativeQuery(sqlBuilder.toString());
            }
            candidatesList = (List<Object[]>)query.getResultList();
            if (candidatesList.isEmpty()) {
                response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
            }
            else {
                for ( Object[] req : candidatesList) {
                     CandidateData transferObj = new CandidateData();
                    transferObj.setCandidateid(req[0].toString());
                    transferObj.setBdmReqId(req[1].toString());
                    transferObj.setClientName((String)req[2]);
                    transferObj.setCandidateSubmitionDate(req[3].toString());
                    transferObj.setCandidateStatus((String)req[4]);
                    transferObj.setCandidateName((String)req[5]);
                    transferObj.setNameOfTheReq((String)req[6]);
                    transferObj.setNameOftheRecruiter((String)req[7]);
                    transferObj.setOfferStatus((String)req[8]);
                    reqList.add(transferObj);
                }
                response = new Response(ExceptionMessage.OK, (Object)reqList);
            }
            totalRecords = Integer.valueOf(this.getEntityManager().createNativeQuery(countQuery).getSingleResult().toString());
            response.setTotalRecords(totalRecords);
            if (totalRecords == 0) {
                response.setTotalRecords(totalRecords);
                response.setTotalPages(Integer.valueOf(0));
            }
            else {
                Integer totalPages = totalRecords / pageSize;
                totalPages = ((totalPages == 0) ? totalPages : (totalPages + 1));
                response.setTotalPages(totalPages);
            }
            return response;
        }
        catch (PersistenceException pe) {
            pe.printStackTrace();
            return new Response(ExceptionMessage.Exception, "500", "Unknown Column '" + searchField + "' .  Enter Correct Column Name. " + pe.getLocalizedMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Response(ExceptionMessage.Exception, "500", " Unable to Search Candidates " + e.getLocalizedMessage());
        }
    }
    
    public CandidateMapping findMappedCandidate(Long candidateId, Long reqId, Long userId, Long regId) {
         Query query = this.getEntityManager().createNativeQuery("SELECT id,candidateStatus,bdmReq_id,candidate_id FROM candidatemapping where candidate_id= ? AND bdmReq_id = ? AND mappedUser_id= ?  and reg_id = ?");
        query.setParameter(1, (Object)candidateId);
        query.setParameter(2, (Object)reqId);
        query.setParameter(3, (Object)userId);
        query.setParameter(4, (Object)regId);
         List<Object[]> list = (List<Object[]>)query.getResultList();
         CandidateMapping cm = new CandidateMapping();
        BdmReqSearch bdmReqSearch = null;
        if (list.size() > 0) {
            for ( Object[] data : list) {
            	bdmReqSearch = new BdmReqSearch();
            	bdmReqSearch.setBdmId(Long.valueOf(new StringBuilder().append(data[3]).toString()));
            	bdmReqSearch.setRegistrationId(regId);
            	
                cm.setId(Long.valueOf(data[0].toString()));
                cm.setCandidateStatus(Long.valueOf(new StringBuilder().append(data[1]).toString()));
                
                cm.setBdmReq((BdmReq)this.bdmReqDao.getRequirementById(bdmReqSearch));
                cm.setCandidate((Candidate)this.candidateDao.find(Long.valueOf(new StringBuilder().append(data[3]).toString())));
            }
            return cm;
        }
        return null;
    }
    
    @Transactional
    public Response changeCandidateMapping(CandidateMapping candidateMapping) {
        Response response = null;
         CandidateMapping mapObj = this.findMappedCandidate(candidateMapping.getCandidate().getId(), candidateMapping.getBdmReq().getId(), candidateMapping.getMappedUser().getId(), candidateMapping.getRegId());
        if (mapObj != null) {
            final Query deleteQuery = this.getEntityManager().createNativeQuery("DELETE FROM candidatemapping WHERE id = " + mapObj.getId());
            deleteQuery.executeUpdate();
        }
        try {
             Query q = this.getEntityManager().createNativeQuery("INSERT INTO candidatemapping(bdmReq_id,candidate_id,mappedUser_id,lastUpdatedDate,reg_id,stageOfInterview,candidateStatus) VALUES(?,?,?,?,?,?,?)");
            q.setParameter(1, (Object)candidateMapping.getBdmReq().getId());
            q.setParameter(2, (Object)candidateMapping.getCandidate().getId());
            q.setParameter(3, (Object)candidateMapping.getMappedUser().getId());
            q.setParameter(4, (Object)new Date());
            q.setParameter(5, (Object)candidateMapping.getRegId());
            q.setParameter(6, (Object)this.getStageOfInterviewStatusId("screening"));
            q.setParameter(7, (Object)this.getCandidateStatusId("new"));
             int update = q.executeUpdate();
            if (update > 0) {
                response = new Response(ExceptionMessage.OK, "Candidate Successfully Mapped to Requirement Id = " + candidateMapping.getBdmReq().getId());
            }
            else {
                response = new Response(ExceptionMessage.OK, "Candidate Mapping Not Changed");
            }
        }
        catch (Exception e) {
            response = new Response(ExceptionMessage.OK, "Unable to change candidate mapping due to :" + e.getMessage());
        }
        return response;
    }
    
    public List<CandidateData> getCandidatesMappingByStatus(String status) {
        List<CandidateData> reqList = null;
        try {
            reqList = new ArrayList<CandidateData>();
            String sql = null;
            Query query = null;
            if (status == null) {
                sql = " select * from getmappingcandetails c where c.onBoardedStatus is not null ";
            }
            else {
                sql = " select * from getmappingcandetails ";
            }
            if (status == null) {
                sql = String.valueOf(sql) + "\tgroup by mappedUser_id ";
            }
            query = this.getEntityManager().createNativeQuery(sql);
             List<Object[]> searchList = (List<Object[]>)query.getResultList();
            if (searchList == null || searchList.isEmpty()) {
                return new ArrayList<CandidateData>();
            }
            for ( Object[] req : searchList) {
                 CandidateData transferObj = new CandidateData();
                transferObj.setCandidateid(req[0].toString());
                transferObj.setBdmReqId(req[1].toString());
                transferObj.setClientName((String)req[2]);
                if (req[3] != null) {
                    transferObj.setCandidateSubmitionDate(Long.valueOf(((Date)req[3]).getTime()).toString());
                }
                transferObj.setCandidateStatus((String)req[4]);
                transferObj.setCandidateName((String)req[5]);
                transferObj.setNameOfTheReq((String)req[6]);
                transferObj.setOfferStatus((String)req[8]);
                transferObj.setNameOftheRecruiter(new StringBuilder().append(req[9]).toString());
                transferObj.setUserId(Long.valueOf(Long.parseLong(new StringBuilder().append(req[10]).toString())));
                transferObj.setLastUpdatedDate((Date)req[11]);
                transferObj.setTypeOfHiring(new StringBuilder().append(req[16]).toString());
                transferObj.setCtc(new StringBuilder().append(req[17]).toString());
                reqList.add(transferObj);
            }
        }
        catch (Exception ex) {}
        return reqList;
    }
    
    @Transactional
    public Response compareCandidateAndRequirment( Candidate candidate,  BdmReq requirements,  Long regId) {
        Response response = null;
        CandidateRequirementMatcher compare = null;
        Query query = null;
        RequirementDetails reqDetails = null;
        CandidateDetails canDetails = null;
        try {
            query = this.getEntityManager().createNativeQuery("select candidate_id from candidatemapping where bdmReq_id = ?");
            query.setParameter(1, (Object)requirements.getId());
            if (query.getResultList().isEmpty()) {
                return new Response(ExceptionMessage.ExcepcetdDataNotAvilable, "The Candidate with id :" + candidate.getId() + " is not mapped with requirement id :" + requirements.getId());
            }
            compare = new CandidateRequirementMatcher();
            if (candidate.getTotalExperience() != null && !candidate.getTotalExperience().isEmpty() && requirements.getTotalExperience() != null && !requirements.getTotalExperience().isEmpty()) {
                compare.setRelavantExperience(true);
                compare.setTotalExperience(true);
            }
            else {
                compare.setRelavantExperience(false);
                compare.setTotalExperience(false);
            }
            if (candidate.getExpectedCTC() != null && !candidate.getExpectedCTC().isEmpty() && requirements.getMaxBudget() != null &&  !requirements.getMaxBudget().isEmpty()) {
                 Double expetedCTC = Double.parseDouble(candidate.getExpectedCTC());
                 Double maxBudget = Double.parseDouble(requirements.getMaxBudget());
                if (expetedCTC <= maxBudget) {
                    compare.setExpectedCTC(true);
                }
                else {
                    compare.setExpectedCTC(false);
                }
            }
            int count = 0;
            if (candidate.getPrimarySkills() != null && requirements.getSkills() != null) {
                 List<Skill> bdmSkills = (List<Skill>)requirements.getSkills();
                 Set<String> bdmSkillSet = new HashSet<String>();
                for ( Skill skill : bdmSkills) {
                    bdmSkillSet.add(skill.getSkillName());
                }
                for ( Skill skill : candidate.getPrimarySkills()) {
                    bdmSkillSet.contains(skill.getSkillName());
                    ++count;
                }
                if (count > 0) {
                    compare.setSkills(true);
                }
                else {
                    compare.setSkills(false);
                }
            }
            canDetails = new CandidateDetails();
            if(candidate.getId()!= null) {
            	canDetails.setId(candidate.getId());
            }if(candidate.getPrimarySkills()!= null) {
            	canDetails.setSkills(candidate.getPrimarySkills());
            }if(candidate.getTotalExperience()!= null) {
            	canDetails.setTotalExperience(candidate.getTotalExperience());
            }if(candidate.getCurrentCTC()!= null) {
            	canDetails.setExpectedCTC(candidate.getCurrentCTC());
            }
            reqDetails = new RequirementDetails();
            if(requirements.getMaxBudget()!= null) {
            	reqDetails.setExpectedCTC(requirements.getMaxBudget());
            }if(requirements.getId()!= null) {
            	reqDetails.setId(requirements.getId());
            }if(requirements.getSkills()!= null) {
            	reqDetails.setSkills(requirements.getSkills());
            }if(requirements.getTotalExperience()!= null) {
            	reqDetails.setTotalExperience(requirements.getTotalExperience());
            }if(requirements.getRelavantExperience()!= null) {
            	reqDetails.setRelavantExperience(requirements.getRelavantExperience().toString());
            }
            compare.setCandidateDetails(canDetails);
            compare.setRequirementDetails(reqDetails);
            response = new Response(ExceptionMessage.OK, (Object)compare);
        }
        catch (Exception e) {
        	e.printStackTrace();
            response = new Response(ExceptionMessage.Exception, e.getMessage());
        }
        return response;
    }
    
    @Transactional
    public Response reviewProfile(CandidateMappingRequest candidateMapping) {
        Query query = null;
        Response response = null;
        try {
            query = this.getEntityManager().createNativeQuery("update candidatemapping set remarks =?,lastUpdatedDate=?,candidateStatus =?,diff_mapping_review_days=? where id=? and reg_id=?");
            query.setParameter(1, (Object)candidateMapping.getRemarks());
            query.setParameter(2, (Object)candidateMapping.getLastUpdatedDate());
            query.setParameter(3, (Object)candidateMapping.getCandidateStatusId());
            query.setParameter(4, (Object)candidateMapping.getDiffOfMappedReviewDays());
            query.setParameter(5, (Object)candidateMapping.getId());
            query.setParameter(6, (Object)candidateMapping.getRegId());
            System.out.println(" candidateMapping.getRemarks()-----:" + candidateMapping.getRemarks() + ":---candidateMapping.getId()---:" + candidateMapping.getId() + ":---candidateMapping.getRegId()--:" + candidateMapping.getRegId());
             int result = query.executeUpdate();
            if (result != 0 && candidateMapping.isStatusFlag()) {
                response = new Response(ExceptionMessage.StatusSuccess, "Profile Revied");
            }
            else {
                response = new Response(ExceptionMessage.StatusSuccess, "Profile Rejected");
            }
        }
        catch (Exception e) {
            response = new Response(ExceptionMessage.Exception, e.getMessage());
        }
        return response;
    }
    
    @Transactional
    public Response submitToCustomer(CandidateMappingRequest candidateMapping) {
        Response response = null;
        Query query = null;
        try {
            if (candidateMapping.getCandidateStatus().equalsIgnoreCase("Submit to Client")) {
                query = this.getEntityManager().createNativeQuery("update candidatemapping set candidateStatus =?,lastUpdatedDate=?,stageOfInterview =?,  diff_review_submit_days=?,diff_submit_short_listed_days=? ,client_comments=?  where id=? and reg_id=?");
            }
            else {
                query = this.getEntityManager().createNativeQuery("update candidatemapping set candidateStatus =?,lastUpdatedDate=?,stageOfInterview =?,  diff_review_submit_days=?,diff_submit_short_listed_days=? where id=? and reg_id=?");
            }
            query.setParameter(1, (Object)candidateMapping.getCandidateStatusId());
            query.setParameter(2, (Object)candidateMapping.getLastUpdatedDate());
            query.setParameter(3, (Object)candidateMapping.getStageOfInterview());
            query.setParameter(4, (Object)candidateMapping.getDiffOfReviewSubmitDays());
            query.setParameter(5, (Object)candidateMapping.getDiffOfSubmitShortListedDays());
            if (candidateMapping.getCandidateStatus().equalsIgnoreCase("Submit to Client")) {
                query.setParameter(6, (Object)candidateMapping.getRemarks());
                query.setParameter(7, (Object)candidateMapping.getId());
                query.setParameter(8, (Object)candidateMapping.getRegId());
            }
            else {
                query.setParameter(6, (Object)candidateMapping.getId());
                query.setParameter(7, (Object)candidateMapping.getRegId());
            }
             int result = query.executeUpdate();
            if (result != 0) {
                if (candidateMapping.getCandidateStatus().equalsIgnoreCase("approved") && candidateMapping.isStatusFlag()) {
                    response = new Response(ExceptionMessage.StatusSuccess, "Candidate details submitted to Client");
                }
                else if (candidateMapping.getCandidateStatus().equalsIgnoreCase("approved") && !candidateMapping.isStatusFlag()) {
                    response = new Response(ExceptionMessage.StatusSuccess, "Candidate Rejected ");
                }
                else if (candidateMapping.isStatusFlag()) {
                    response = new Response(ExceptionMessage.StatusSuccess, "Candidate shortlisted by client");
                }
                else {
                    response = new Response(ExceptionMessage.StatusSuccess, "Candidate rejected by client");
                }
            }
        }
        catch (Exception e) {
            response = new Response(ExceptionMessage.Exception, e.getMessage());
        }
        return response;
    }
    
    public Long getCandidateStatusId(String status) {
        Query query = null;
        Number number = 0;
        try {
            query = this.getEntityManager().createNativeQuery("SELECT id FROM candidatestatus where status=?");
            query.setParameter(1, (Object)status);
            number = (Number)query.getSingleResult();
        }
        catch (Exception ex) {}
        return number.longValue();
    }
    
    public Long getStageOfInterviewStatusId(String status) {
        Query query = null;
        Number number = 0;
        try {
            query = this.getEntityManager().createNativeQuery("SELECT id FROM assesmentname where assesmentName=?");
            query.setParameter(1, (Object)status);
            number = (Number)query.getSingleResult();
        }
        catch (Exception ex) {}
        return number.longValue();
    }
    
    public int checkDuplicateMapping(Long candidateId, Long reqId, Long userId, Long regId) {
        Query query = null;
        Number number = 0;
        try {
            query = this.getEntityManager().createNativeQuery("SELECT count(id) FROM candidatemapping where candidate_id= ? AND bdmReq_id = ? AND mappedUser_id= ?  and reg_id = ?");
            query.setParameter(1, (Object)candidateId);
            query.setParameter(2, (Object)reqId);
            query.setParameter(3, (Object)userId);
            query.setParameter(4, (Object)regId);
            number = (Number)query.getSingleResult();
        }
        catch (Exception ex) {}
        return number.intValue();
    }
    
    public Response getMappingByStatus( CandidateMappingRequest candidateMapping) {
        Query query = null;
        List<Object[]> result = null;
        MappingStatuses mappingStatuses = null;
        List<MappingStatuses> mapplingList = new ArrayList<MappingStatuses>();
        StringBuilder queryString = new StringBuilder();
        StringBuilder countQueryString = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Integer totalRecords = 0;
        Response response = null;
        List<Object[]> mapping = null;
        Query countQuery = null;
        countQueryString.append(" select count(can.id) as count from candidatemapping can  inner join bdmreq req "
        		+ " on req.id = can.bdmReq_id   inner join client cli on cli.id = req.client_id "
        		+ " inner join assesmentname asn on can.stageOfInterview=asn.id  "
        		+ " inner join candidatestatus cs on can.candidateStatus=cs.id  where can.reg_id = ? ");
        queryString.append(" select req.nameOfRequirement as requirement ,cli.clientName as clineName,can.bdmReq_id,count(can.id) as count, assesmentName as stage  "
        		+ "  from candidatemapping can  inner join bdmreq req on req.id = can.bdmReq_id "
        		+ "  inner join client cli on cli.id = req.client_id  inner join assesmentname asn "
        		+ " on can.stageOfInterview=asn.id   inner join candidatestatus cs on can.candidateStatus=cs.id "
        		+ " where can.reg_id = ? ");
        if (candidateMapping.getId() != null) {
            queryString.append(" and can.id=? ");
            countQueryString.append(" and can.id=? ");
        }
        if (candidateMapping.getClientName() != null && !candidateMapping.getClientName().isEmpty()) {
            queryString.append(" and cli.clientName like '" + candidateMapping.getClientName() + "%'");
            countQueryString.append(" and cli.clientName like '" + candidateMapping.getClientName() + "%'");
        }
        if (candidateMapping.getRequirementName() != null && !candidateMapping.getRequirementName().isEmpty()) {
            queryString.append(" and req.nameOfRequirement like '" + candidateMapping.getRequirementName() + "%'");
            countQueryString.append(" and req.nameOfRequirement like '" + candidateMapping.getRequirementName() + "%'");
        }
        if (candidateMapping.getFromDate() != null && candidateMapping.getToDate() != null) {
            queryString.append(" and can.lastUpdatedDate between  '" + dateFormat.format(candidateMapping.getFromDate()) +
            		"' and '" + dateFormat.format(candidateMapping.getToDate()) + "'");
            countQueryString.append(" and can.lastUpdatedDate between  '" + dateFormat.format(candidateMapping.getFromDate()) +
            		"' and '" + dateFormat.format(candidateMapping.getToDate()) + "'");
        }
        if (candidateMapping.getFromDate() != null && candidateMapping.getToDate() == null) {
            queryString.append(" and can.lastUpdatedDate between  '" + dateFormat.format(candidateMapping.getFromDate()) + 
            		"' and now() ");
            countQueryString.append(" and can.lastUpdatedDate between  '" + dateFormat.format(candidateMapping.getFromDate()) + 
            		"' and now() ");
        }
        if (candidateMapping.getFromDate() == null && candidateMapping.getToDate() != null) {
            queryString.append(" and can.lastUpdatedDate like  '" + dateFormat.format(candidateMapping.getToDate())+ "%'");
            countQueryString.append(" and can.lastUpdatedDate like  '" + dateFormat.format(candidateMapping.getToDate())+"%'" );
        }
        queryString.append(" group by req.id , cli.id, asn.assesmentName  ");
       // countQueryString.append(" group by req.id , cli.id, asn.assesmentName");
        int startingRow = 0;
        System.out.println("candidateMapping.getPageNum()----------" + candidateMapping.getPageNum());
        if (candidateMapping.getPageNum() == 1) {
            startingRow = 0;
        }
        else {
            startingRow = (candidateMapping.getPageNum() - 1) * candidateMapping.getPageSize();
        }
        if (candidateMapping.isFlag()) {
            try {
                queryString.append("   limit " + startingRow + "," + candidateMapping.getPageSize());
                query = this.getEntityManager().createNativeQuery(queryString.toString());
                query.setParameter(1, (Object)candidateMapping.getRegId());
                if (candidateMapping.getId() != null) {
                    query.setParameter(2, (Object)candidateMapping.getId());
                }
              //  int recordsCount = 0;
                countQuery = this.getEntityManager().createNativeQuery(countQueryString.toString());
                countQuery.setParameter(1, (Object)candidateMapping.getRegId());
                if (candidateMapping.getId() != null) {
                	countQuery.setParameter(2, (Object)candidateMapping.getId());  
                }
                Number count = (Number)countQuery.getSingleResult();
                totalRecords = count.intValue();   
                result = (List<Object[]>)query.getResultList();
                if (result == null) {
                    response = new Response(ExceptionMessage.DataIsEmpty);
                }
                for (final Object[] object : result) {                	
                    mappingStatuses = new MappingStatuses();
                    mappingStatuses.setRequirementName(String.valueOf(new StringBuilder().append(object[0]).toString()));
                    mappingStatuses.setClientName(String.valueOf(new StringBuilder().append(object[1]).toString()));
                    mappingStatuses.setBdmReqId(Long.valueOf(new StringBuilder().append(object[2]).toString()));
                    mappingStatuses.setStatusCount((int)Integer.valueOf(new StringBuilder().append(object[3]).toString()));
                    mappingStatuses.setStages(String.valueOf(new StringBuilder().append(object[4]).toString()));
                   
                    mapplingList.add(mappingStatuses);
                    if(mappingStatuses.getStages().equalsIgnoreCase("Screening")) {
                    	mappingStatuses.setScreeningCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Submission")) {
                    	mappingStatuses.setSubmissionCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Interveiw")) {
                    	mappingStatuses.setInterviewCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Offered")) {
                    	mappingStatuses.setOfferCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Hiring")) {
                    	mappingStatuses.setHiredCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Others")) {
                    	mappingStatuses.setOtherCount(mappingStatuses.getStatusCount());
                    }
                 //   recordsCount = recordsCount++;
                }
                response = new Response(ExceptionMessage.OK, (Object)mapplingList);
               // totalRecords = recordsCount;
                Integer totalPages = 1;
                if (totalRecords == 0) {
                    response.setTotalPages(Integer.valueOf(0));
                }
                else if (totalRecords == 1 || candidateMapping.getPageSize() >= totalRecords) {
                    response.setTotalPages(totalPages);
                }
                else {
                    totalPages = totalRecords / candidateMapping.getPageSize();
                    System.out.println(totalPages);
                    totalPages = ((totalRecords % candidateMapping.getPageSize() > 0) ? (totalPages + 1) : totalPages);
                }
            }
            catch (Exception e) {
                response = new Response(ExceptionMessage.Exception, e.getMessage());
            }
            return response;
        }
        FileOutputStream out = null;
        File file = null;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Requirement Mapping Details");
        Font headerFont = (Font)workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)14);
        CellStyle backgroundStyle = (CellStyle)workbook.createCellStyle();
        backgroundStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerCellStyle = (CellStyle)workbook.createCellStyle();
        Query queryExc = null;
        queryExc = this.getEntityManager().createNativeQuery(queryString.toString());
        queryExc.setParameter(1, (Object)candidateMapping.getRegId());
        mapping = (List<Object[]>)queryExc.getResultList();
        Row header = (Row)sheet.createRow(0);
        header.createCell(0).setCellValue("Requirement Name");
        header.createCell(1).setCellValue("Client Name");
        header.createCell(2).setCellValue("Requirement Id");
        header.createCell(3).setCellValue("Count");
        header.createCell(4).setCellValue("stage");
        int rowCount = 0;
        for (Object[] obj : mapping) {
             Row row = (Row)sheet.createRow(++rowCount);
            int columnCount = -1;
            int col = 0;
            Object[] array;
            for (int length = (array = obj).length, i = 0; i < length; ++i) {
                 Object object2 = array[i];
                if (col != 37) {
                     Cell cell = row.createCell(++columnCount);
                    if (object2 instanceof Integer) {
                        cell.setCellValue((double)Long.valueOf(new StringBuilder().append(object2).toString()));
                    }
                    else if (object2 instanceof String) {
                        cell.setCellValue(new StringBuilder().append(object2).toString());
                    }
                    else if (object2 instanceof Long) {
                        cell.setCellValue((double)Long.valueOf(new StringBuilder().append(object2).toString()));
                    }
                    else if (object2 instanceof Boolean) {
                        cell.setCellValue((boolean)Boolean.valueOf(new StringBuilder().append(object2).toString()));
                    }
                    else {
                        cell.setCellValue(new StringBuilder().append(object2).toString());
                    }
                }
                ++col;
            }
        }
        try {
            StringBuilder pro = new StringBuilder(this.excelFilePath);
            String strDate = dateFormat.format(new Date());
            pro.append("RequirementMappingDetails" + strDate + "File");
            file = new File((Object)pro + ".xlsx");
            out = new FileOutputStream(file);
            workbook.write((OutputStream)out);
            byte[] encodedBytes = null;
            Path p = Paths.get(file.getAbsolutePath(), new String[0]);
            encodedBytes = Files.readAllBytes(p);
            response = new Response(ExceptionMessage.StatusSuccess, (Object)encodedBytes);
        }
        catch (Exception e2) {
            e2.printStackTrace();
            response = new Response(ExceptionMessage.Exception);
            if (file.exists()) {
                file.delete();
            }
            try {
                out.close();
                workbook.close();
            }
            catch (Exception e3) {
                response = new Response(ExceptionMessage.Bad_Request, e3.getLocalizedMessage());
            }
            return response;
        }
        finally {
            if (file.exists()) {
                file.delete();
            }
            try {
                out.close();
                workbook.close();
            }
            catch (Exception e3) {
                response = new Response(ExceptionMessage.Bad_Request, e3.getLocalizedMessage());
            }
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            out.close();
            workbook.close();
        }
        catch (Exception e3) {
            response = new Response(ExceptionMessage.Bad_Request, e3.getLocalizedMessage());
        }
        return response;
    }
    
    public Response getMappingDetails( CandidateMappingRequest candidateMapping) {
        Query query = null;
        Query countQuery = null;
        List<Object[]> result = null;         
        MappingDetails mappingDetails = null;
         List<MappingDetails> mappingList = new ArrayList<MappingDetails>();
         StringBuilder queryString = new StringBuilder();
         StringBuilder countQueryString = new StringBuilder();
         SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Response response = null;
        Integer totalRecords = 0;  
        List<Object[]> resultExe = null;
        
            countQueryString.append("select count(*)   from candidatemapping can  inner join assesmentname asn on can.stageOfInterview=asn.id  "
            		+ "inner join bdmreq bdm on bdm.id=can.bdmReq_id inner join client cli on cli.id=bdm.client_id  "
            		+ " inner join user usr on usr.id =can.mappedUser_id  inner join candidate cand on cand.id =can.candidate_id  "
            		+ "inner join candidatestatus cs on can.candidateStatus=cs.id  inner join user u1 on u1.id=can.mappedUser_id "
            		+ "inner join user u2 on u2.id=cli.leadId  inner join user u3 on u3.id=cli.accountManagerId where can.reg_id=? "
            		+ " and assesmentName=? ");
            queryString.append("select can.id,can.bdmReq_id, bdm.nameOfRequirement,cli.clientName,can.candidate_id,  "
            		+ "concat(cand.firstName,' ', cand.lastName) AS candidateName,"
            		+ "can.mappedUser_id,concat(usr.firstName,' ',usr.lastName) as userName,"
            		+ "  concat(u1.firstName,' ', u1.lastName) AS mappedUserName, "
            		+ " can.candidateStatus, cs.status,can.stageOfInterview,assesmentName as stage "           		
            		+ ",  cli.email as clentEmail, u1.email as recruiterEmail , u2.email as leadEmail, u3.email as amEmail "
            		+ "  from candidatemapping can  inner join assesmentname asn on can.stageOfInterview=asn.id  "
            		+ "inner join bdmreq bdm on bdm.id=can.bdmReq_id inner join client cli on cli.id=bdm.client_id  "
            		+ " inner join user usr on usr.id =can.mappedUser_id  inner join candidate cand on cand.id =can.candidate_id "
            		+ "  inner join user u1 on u1.id=can.mappedUser_id inner join user u2 on u2.id=cli.leadId "
            		+ " inner join user u3 on u3.id=cli.accountManagerId   inner join candidatestatus cs on can.candidateStatus=cs.id "
            		+ " where can.reg_id=?  and assesmentName=? ");            
           
            if (candidateMapping.getClientName() != null) {            	
                queryString.append(" and cli.clientName like '" + candidateMapping.getClientName() + "%'");
                countQueryString.append(" and cli.clientName like '" + candidateMapping.getClientName() + "%'");
            }
            if (candidateMapping.getRequirementName() != null) {            	
                queryString.append(" and bdm.nameOfRequirement like '" + candidateMapping.getRequirementName() + "%'");
                countQueryString.append(" and bdm.nameOfRequirement like '" + candidateMapping.getRequirementName() + "%'");
            }
            if (candidateMapping.getFromDate() != null && candidateMapping.getToDate() != null) {            	
                queryString.append(
                        " and can.lastUpdatedDate between  '" + dateFormat.format(candidateMapping.getFromDate())
                                + "' and '" + dateFormat.format(candidateMapping.getToDate()) + "'");
                countQueryString.append(" and can.lastUpdatedDate between  '" + dateFormat.format(candidateMapping.getFromDate())
                                + "' and '" + dateFormat.format(candidateMapping.getToDate()) + "'");
            }
            if (candidateMapping.getFromDate() != null && candidateMapping.getToDate() == null) {            	
                queryString.append(" and can.lastUpdatedDate between  '"
                        + dateFormat.format(candidateMapping.getFromDate()) + "' and now() ");
                countQueryString.append(" and can.lastUpdatedDate between  '"
                        + dateFormat.format(candidateMapping.getFromDate()) + "' and now() ");
            }
            if (candidateMapping.getFromDate() == null && candidateMapping.getToDate() != null) {            	
                queryString.append(
                        " and can.lastUpdatedDate like  '" + dateFormat.format(candidateMapping.getToDate()) + "%'");
                countQueryString.append(" and can.lastUpdatedDate like  '" + dateFormat.format(candidateMapping.getToDate()) + "%'");
            }
           
            if (candidateMapping.getBdmReq() != null ) {
                queryString.append(" and can.bdmReq_id=" + candidateMapping.getBdmReq().getId());
                countQueryString.append(" and can.bdmReq_id=" + candidateMapping.getBdmReq().getId());               
            }
            if (candidateMapping.getCandidateStatus() != null) {
              //  queryString.append(" and can.bdmReq_id=" + candidateMapping.getBdmReq().getId());
             //   countQueryString.append(" and can.bdmReq_id=" + candidateMapping.getBdmReq().getId());
                if (candidateMapping.getCandidateStatus() != null && candidateMapping.getCandidateStatus().equalsIgnoreCase("Shortlisted")) {
                    queryString.append(" and cs.status='" + candidateMapping.getCandidateStatus() + "'");
                	countQueryString.append(" and cs.status='" + candidateMapping.getCandidateStatus() + "'");
                	}
//                else {
//                    queryString.append(" and cs.status='Shortlisted'");
//                	countQueryString.append(" and cs.status='Shortlisted'");
//                }
            }     
            
            if (candidateMapping.getId() != null) {               
                queryString.append(" and can.id = " + candidateMapping.getId());
                countQueryString.append(" and can.id = " + candidateMapping.getId());
            }           
            int startingRow = 0;
            if (candidateMapping.getPageNum() == 1) {                
                startingRow = 0;
            }
            else {                
                startingRow = (candidateMapping.getPageNum() - 1) * candidateMapping.getPageSize();
            }
		if (candidateMapping.isFlag()) {
			queryString.append(" order by can.id DESC LIMIT " + startingRow + "," + candidateMapping.getPageSize());

			query = this.getEntityManager().createNativeQuery(queryString.toString());
			query.setParameter(1, (Object) candidateMapping.getRegId());
			query.setParameter(2, (Object) candidateMapping.getStage());
			countQuery = this.getEntityManager().createNativeQuery(countQueryString.toString());
			countQuery.setParameter(1, (Object) candidateMapping.getRegId());
			countQuery.setParameter(2, (Object) candidateMapping.getStage());
			try {
				result = (List<Object[]>) query.getResultList();
				Number count = (Number) countQuery.getSingleResult();
				totalRecords = count.intValue();
				for (Object[] object : result) {
					mappingDetails = new MappingDetails();
					if (object[0] != null) {
						mappingDetails.setMappingId(Long.valueOf(new StringBuilder().append(object[0]).toString()));
					}
					if (object[1] != null) {
						mappingDetails.setBdmReqId(Long.valueOf(new StringBuilder().append(object[1]).toString()));
					}
					if (object[2] != null) {
						mappingDetails
								.setReqirementName(String.valueOf(new StringBuilder().append(object[2]).toString()));
					}
					if (object[3] != null) {
						mappingDetails.setClientName(String.valueOf(new StringBuilder().append(object[3]).toString()));
					}
					if (object[4] != null) {
						mappingDetails.setCandidateId(Long.valueOf(new StringBuilder().append(object[4]).toString()));
					}
					if (object[5] != null) {
						mappingDetails
								.setCandidateName(String.valueOf(new StringBuilder().append(object[5]).toString()));
					}
					if (object[6] != null) {
						mappingDetails.setUserId(Long.valueOf(new StringBuilder().append(object[6]).toString()));
					}
					if (object[7] != null) {
						mappingDetails.setUserName(String.valueOf(new StringBuilder().append(object[7]).toString()));
					}
					if (object[8] != null) {
						mappingDetails
								.setRecruiterName(String.valueOf(new StringBuilder().append(object[8]).toString()));
					}
					if (object[9] != null) {
						mappingDetails.setStatusId(Long.valueOf(new StringBuilder().append(object[9]).toString()));
					}
					if (object[10] != null) {
						mappingDetails.setStatus(String.valueOf(new StringBuilder().append(object[10]).toString()));
					}
					if (object[11] != null) {
						mappingDetails.setStageId(Long.valueOf(new StringBuilder().append(object[11]).toString()));
					}
					if (object[12] != null) {
						mappingDetails.setStage(String.valueOf(new StringBuilder().append(object[12]).toString()));
					}		
					
					if (object[13] != null) {
						mappingDetails
								.setClientEmail(String.valueOf(new StringBuilder().append(object[13]).toString()));
					}
					if (object[14] != null) {
						mappingDetails
								.setRecruiterEmail(String.valueOf(new StringBuilder().append(object[14]).toString()));
					}
					if (object[15] != null) {
						mappingDetails.setLeadEmail(String.valueOf(new StringBuilder().append(object[15]).toString()));
					}
					if (object[16] != null) {
						mappingDetails.setAmEmail(String.valueOf(new StringBuilder().append(object[16]).toString()));
					}
					mappingList.add(mappingDetails);
				}
				response = new Response(ExceptionMessage.OK, (Object) mappingList);
				Integer totalPages = 1;
				if (totalRecords == 0) {
					response.setTotalPages(Integer.valueOf(0));
				} else {
					totalPages = totalRecords / candidateMapping.getPageSize();
					totalPages = ((totalRecords % candidateMapping.getPageSize() > 0) ? (totalPages + 1) : totalPages);
				}

				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);
				response.setResult((Object) mappingList);
			} catch (Exception e) {
				e.printStackTrace();
				response = new Response(ExceptionMessage.Exception, e.getMessage());
			}
			 return response;
		}else {
			FileOutputStream out = null;
	        File file = null;
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Requirement Mapping Details");
	        Font headerFont = (Font)workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short)14);
	        CellStyle backgroundStyle = (CellStyle)workbook.createCellStyle();
	        backgroundStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	        backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerFont.setColor(IndexedColors.BLACK.getIndex());
	        CellStyle headerCellStyle = (CellStyle)workbook.createCellStyle();
	        Query queryExc = null;
	        queryExc = this.getEntityManager().createNativeQuery(queryString.toString());
	        queryExc.setParameter(1, (Object) candidateMapping.getRegId());
	        queryExc.setParameter(2, (Object) candidateMapping.getStage());
			countQuery = this.getEntityManager().createNativeQuery(countQueryString.toString());
			countQuery.setParameter(1, (Object) candidateMapping.getRegId());
			countQuery.setParameter(2, (Object) candidateMapping.getStage());
			resultExe = (List<Object[]>)queryExc.getResultList();
	        Row header = (Row)sheet.createRow(0);
	        header.createCell(0).setCellValue("Mapping Id");
	        header.createCell(1).setCellValue("Requirement Id");
	        header.createCell(2).setCellValue("Name Of The Requirement");
	        header.createCell(3).setCellValue("Client Name");
	        header.createCell(4).setCellValue("Candidate Id");
	        header.createCell(5).setCellValue("Candidate Name");
	        header.createCell(6).setCellValue("User Id");
	        header.createCell(7).setCellValue("User Name");
	        header.createCell(8).setCellValue("Recruiter Name");
	        header.createCell(9).setCellValue("Candidate Status Id");
	        header.createCell(10).setCellValue("Candidate Status");
	        header.createCell(11).setCellValue("Stage Id");
	        header.createCell(12).setCellValue("Stage");
	        header.createCell(13).setCellValue("Client Email");
	        header.createCell(14).setCellValue("Recruiter Email");
	        header.createCell(15).setCellValue("Lead Email");
	        header.createCell(16).setCellValue("AM Email");
	        int rowCount = 0;
	        for (Object[] obj : resultExe) {
	             Row row = (Row)sheet.createRow(++rowCount);
	            int columnCount = -1;
	            int col = 0;
	            Object[] array;
	            for (int length = (array = obj).length, i = 0; i < length; ++i) {
	                 Object object2 = array[i];
	                if (col != 37) {
	                     Cell cell = row.createCell(++columnCount);
	                    if (object2 instanceof Integer) {
	                        cell.setCellValue((double)Long.valueOf(new StringBuilder().append(object2).toString()));
	                    }
	                    else if (object2 instanceof String) {
	                        cell.setCellValue(new StringBuilder().append(object2).toString());
	                    }
	                    else if (object2 instanceof Long) {
	                        cell.setCellValue((double)Long.valueOf(new StringBuilder().append(object2).toString()));
	                    }
	                    else if (object2 instanceof Boolean) {
	                        cell.setCellValue((boolean)Boolean.valueOf(new StringBuilder().append(object2).toString()));
	                    }
	                    else {
	                        cell.setCellValue(new StringBuilder().append(object2).toString());
	                    }
	                }
	                ++col;
	            }
	        }
	        try {
	            StringBuilder pro = new StringBuilder(this.excelFilePath);
	            String strDate = dateFormat.format(new Date());
	            pro.append("RequirementMappingDetailsList" + strDate + "File");
	            file = new File((Object)pro + ".xlsx");
	            out = new FileOutputStream(file);
	            workbook.write((OutputStream)out);
	            byte[] encodedBytes = null;
	            Path p = Paths.get(file.getAbsolutePath(), new String[0]);
	            encodedBytes = Files.readAllBytes(p);
	            response = new Response(ExceptionMessage.StatusSuccess, (Object)encodedBytes);
	        }
	        catch (Exception e2) {
	            e2.printStackTrace();
	            response = new Response(ExceptionMessage.Exception);
	            if (file.exists()) {
	                file.delete();
	            }
	            try {
	                out.close();
	                workbook.close();
	            }
	            catch (Exception e3) {
	                response = new Response(ExceptionMessage.Bad_Request, e3.getLocalizedMessage());
	            }
	            return response;
	        }
	        finally {
	            if (file.exists()) {
	                file.delete();
	            }
	            try {
	                out.close();
	                workbook.close();
	            }
	            catch (Exception e3) {
	                response = new Response(ExceptionMessage.Bad_Request, e3.getLocalizedMessage());
	            }
	        }
	        if (file.exists()) {
	            file.delete();
	        }
	        try {
	            out.close();
	            workbook.close();
	        }
	        catch (Exception e3) {
	            response = new Response(ExceptionMessage.Bad_Request, e3.getLocalizedMessage());
	        }
	        return response;
		}
       
    }
    
    
    @Transactional
    public Response updateOfferRelease( CandidateMappingRequest candidateMapping) {
        Response response = null;
        Query query = null;
        try {
            query = this.getEntityManager().createNativeQuery("update candidatemapping set "
            		+ " candidateStatus =?,lastUpdatedDate=?,stageOfInterview =?, "
            		+ " diff_review_submit_days=?,ctc_offered=? ,offer_release_date=? "
            		+ "  where id=? and reg_id=?");
            
            query.setParameter(1, (Object)candidateMapping.getCandidateStatusId());
            query.setParameter(2, (Object)candidateMapping.getLastUpdatedDate());
            query.setParameter(3, (Object)candidateMapping.getStageOfInterview());
            query.setParameter(4, (Object)candidateMapping.getDiffOfReviewSubmitDays());
            query.setParameter(5, (Object)candidateMapping.getCtcOffered());
            query.setParameter(6, (Object)candidateMapping.getOfferReleaseDate());
            query.setParameter(7, (Object)candidateMapping.getId());
            query.setParameter(8, (Object)candidateMapping.getRegId());
           
            int result = query.executeUpdate();
            if (result != 0) { 
            	response = new Response(ExceptionMessage.StatusSuccess, "Candidate Offer Released");
            	
                
            }
        }
        catch (Exception e) {
            response = new Response(ExceptionMessage.Exception, e.getMessage());
        }
        return response;
    }
    
    @Transactional
    public Response updateOfferStatus(CandidateMappingRequest candidateMapping) {
        Response response = null;
        Query query = null;
        try {
            query = this.getEntityManager().createNativeQuery("update candidatemapping set "
            		+ " candidateStatus =?,lastUpdatedDate=?,stageOfInterview =?, "
            		+ " diff_review_submit_days=?,candidate_comments=? "
            		+ "  where id=? and reg_id=? ");
            
            query.setParameter(1, (Object)candidateMapping.getCandidateStatusId());
            query.setParameter(2, (Object)candidateMapping.getLastUpdatedDate());
            query.setParameter(3, (Object)candidateMapping.getStageOfInterview());
            query.setParameter(4, (Object)candidateMapping.getDiffOfReviewSubmitDays());
            query.setParameter(5, (Object)candidateMapping.getRemarks());
            query.setParameter(6, (Object)candidateMapping.getId());
            query.setParameter(7, (Object)candidateMapping.getRegId());
           
            int result = query.executeUpdate();
            if (result != 0) {               
            	if(candidateMapping.isStatusFlag()) {
            		response = new Response(ExceptionMessage.StatusSuccess, "Candidate Offer Accepted");
            	}else {
            		response = new Response(ExceptionMessage.StatusSuccess, "Candidate Offer Rejected");
            	}
                
            }
        }
        catch (Exception e) {
            response = new Response(ExceptionMessage.Exception, e.getMessage());
        }
        return response;
    }
    
    @Transactional
    public Response updateOnBoardStatus(CandidateMappingRequest candidateMapping) {
        Response response = null;
        Query query = null;
        Query canQuery = null;
        int result = 0;
        try {
        	if(candidateMapping.isStatusFlag()) {
        		query = this.getEntityManager().createNativeQuery("update candidatemapping set "
                		+ " candidateStatus =?,lastUpdatedDate=?,stageOfInterview =?, "
                		+ " diff_review_submit_days=?,date_of_joining=?, ctc_offered=? "            		
                		+ "  where id=? and reg_id=?");
        		canQuery = this.getEntityManager().createNativeQuery("update candidate set "
                		+ " candidateStatusMaster_id =?,expectedCTC=? "          		
                		+ "  where id=? ");
        	}else {            
            query = this.getEntityManager().createNativeQuery("update candidatemapping set "
            		+ " candidateStatus =?,lastUpdatedDate=?,stageOfInterview =?, "
            		+ " diff_review_submit_days=?, "            		
            		+ " reason=? where id=? and reg_id=?");
        	}
            
            query.setParameter(1, (Object)candidateMapping.getCandidateStatusId());
            query.setParameter(2, (Object)candidateMapping.getLastUpdatedDate());
            query.setParameter(3, (Object)candidateMapping.getStageOfInterview());
            query.setParameter(4, (Object)candidateMapping.getDiffOfReviewSubmitDays());
            if(candidateMapping.isStatusFlag()) {
            	query.setParameter(5, (Object)candidateMapping.getDateOfJoining());
            	query.setParameter(6, (Object)candidateMapping.getCtcOffered());            
            	query.setParameter(7, (Object)candidateMapping.getId());
            	query.setParameter(8, (Object)candidateMapping.getRegId());
            	//candidate table update
            	canQuery.setParameter(1, (Object)candidateMapping.getCandidateStatusId());
            	canQuery.setParameter(2, (Object)candidateMapping.getCtcOffered());
            	canQuery.setParameter(3, (Object)candidateMapping.getCandidate().getId());
            	canQuery.executeUpdate();
            	result = query.executeUpdate();
            }else {
            	 query.setParameter(5, (Object)candidateMapping.getRemarks());
                 query.setParameter(6, (Object)candidateMapping.getId());
                 query.setParameter(7, (Object)candidateMapping.getRegId());
                 result = query.executeUpdate();
            }           
            
           
            if (result != 0) {               
            	if(candidateMapping.isStatusFlag()) {
            		response = new Response(ExceptionMessage.StatusSuccess, "Candidate OnBoarded");
            	}else {
            		response = new Response(ExceptionMessage.StatusSuccess, "Candidate Not Joined");
            	}
                
            }
        }
        catch (Exception e) {
            response = new Response(ExceptionMessage.Exception, e.getMessage());
        }
        return response;
    }
    
    public Date getlastUpdatedDate(CandidateMappingRequest candidateMapping) {
    	Date lastUpdatedDate = null;
         StringBuilder builder = new StringBuilder("select lastUpdatedDate  from candidatemapping where id=? and reg_id=? ");
         Query query = this.getEntityManager().createNativeQuery(builder.toString());
        query.setParameter(1, (Object)candidateMapping.getId());
        query.setParameter(2, (Object)candidateMapping.getRegId());
        try {
    		Object object = query.getSingleResult();
    		
    		if(object instanceof Date) {
    			lastUpdatedDate = (Date)object;
    		}		
    		}catch(Exception e) {
    			
    		}
        return lastUpdatedDate;
    }

	@Override
	public int updateStatusByCandidateId(Long id, Long statusId) {
		String sql = "update candidatemapping set candidateStatus = " + statusId + " where candidate_id = " + id;
        return getEntityManager().createNativeQuery(sql).executeUpdate();		
	}
	
	@Override
    public Response getCountByReq(CandidateMappingRequest candidateMapping) {
        Query query = null;
        List<Object[]> result = null;
        MappingStatuses mappingStatuses = null;
        List<MappingStatuses> mapplingList = new ArrayList<MappingStatuses>();
        StringBuilder queryString = new StringBuilder();       
        Response response = null;
        
       
        queryString.append(" SELECT count(can.id),ass.assesmentName FROM testing.candidatemapping can "
        		+ " inner join assesmentname ass on ass.id=can.stageOfInterview where reg_id=? and "
        		+ " bdmReq_id=? group by ass.assesmentName"
        		);
       
            try {               
                query = this.getEntityManager().createNativeQuery(queryString.toString());
                query.setParameter(1, (Object)candidateMapping.getRegId());                
                query.setParameter(2, (Object)candidateMapping.getBdmReq().getId());
                           
                result = (List<Object[]>)query.getResultList();
                if (result == null) {
                    response = new Response(ExceptionMessage.DataIsEmpty);
                }
                mappingStatuses = new MappingStatuses();
                for (final Object[] object : result) {                	
                   
                    mappingStatuses.setStatusCount((int)Integer.valueOf(new StringBuilder().append(object[0]).toString()));
                    mappingStatuses.setStages(String.valueOf(new StringBuilder().append(object[1]).toString()));
                   
                    mapplingList.add(mappingStatuses);
                    if(mappingStatuses.getStages().equalsIgnoreCase("Screening")) {
                    	mappingStatuses.setScreeningCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Submission")) {
                    	mappingStatuses.setSubmissionCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Interveiw")) {
                    	mappingStatuses.setInterviewCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Offered")) {
                    	mappingStatuses.setOfferCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Hiring")) {
                    	mappingStatuses.setHiredCount(mappingStatuses.getStatusCount());
                    }
                    if(mappingStatuses.getStages().equalsIgnoreCase("Others")) {
                    	mappingStatuses.setOtherCount(mappingStatuses.getStatusCount());
                    }
                    mapplingList.add(mappingStatuses);                 
                }
                response = new Response(ExceptionMessage.OK, (Object)mappingStatuses);
              
            }
            catch (Exception e) {
                response = new Response(ExceptionMessage.Exception, e.getMessage());
            }
            return response;
        
    }
        
}
