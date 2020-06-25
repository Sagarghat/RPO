package rpo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateJobTitle.CandidateJobTitleDaoInf;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateJobTitle;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.rest.resources.CandidateResource;
import com.ojas.rpo.security.util.CandidateSearch;

public class CandidateResourceTest {

	@InjectMocks
	private CandidateResource candidateResource;

	@Mock
	private CandidateJobTitleDaoInf candidateJobTitleDaoInf;

	@Mock
	private CandidatelistDao candidatelistDao;

	@Spy
	Candidate candidate = new Candidate();
	
	@Spy
	Response response = new Response();
	
	@Spy
	CandidateSearch candidateSearch = new CandidateSearch();
	
	@Spy
	CandidateJobTitle candidateJobTitle = new CandidateJobTitle();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test 
	public void createTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		List<String> arrayList = new ArrayList<>();
		arrayList.add("Test");
		candidateJobTitle.setId(11L);
		//candidate.setMobile("123456677890");
		//candidate.setEmail("Example@gmail.com");
		//arrayList.add(candidate);
		candidate.setRegistrationId(12L);
		if(candidateJobTitle.getId() != null) {
		when(candidateJobTitleDaoInf.find(candidateJobTitle.getId())).thenReturn(candidateJobTitle);
		when(candidatelistDao.chekduplicate(candidate.getMobile(),candidate.getEmail(),candidate.getRegistrationId())).thenReturn(arrayList);
		when(candidate.getMobile()).thenReturn("1231323");
		when(candidate.getEmail()).thenReturn("Example@gmail.com");
		when(candidatelistDao.find(candidateJobTitle.getId())).thenReturn(candidate);
		Response create = candidateResource.create(candidate, request);
		//ExceptionMessage status = create.getStatus();  
		assertNotNull(create);
		}
	}
	
	@Test
	public void readTest() {
		candidate.setId(11L);
		if (candidate.getId() != null) {
			when(candidatelistDao.find(candidate.getId())).thenReturn(candidate); 
			Response read = candidateResource.read(anyLong());
			//ExceptionMessage status = read.getStatus();
			assertNotNull(read);    
		}
	}  
	@Test
	public void readNullTest() {
		candidate.setId(null);
		response=null;
		if (candidate.getId() == null) {
			when(candidatelistDao.find(candidate.getId())).thenReturn(candidate);
			Response read = candidateResource.read(anyLong());
			ExceptionMessage status = read.getStatus();
			assertEquals(ExceptionMessage.DataIsEmpty, status);
		}
	}
	
	@Test
	public void findDataBasedOnRegIdTest() {
		candidateSearch.setBdmReqId(100L);
		candidateSearch.setRegistrationId(12L);
		candidateSearch.setCandidateId(1L);
		candidateSearch.setCandidateName("Test");
		when(candidatelistDao.findAllCandidateBasedOnRegId(candidateSearch)).thenReturn(response);
		Response findDataBasedOnRegId = candidateResource.findDataBasedOnRegId(candidateSearch);
		assertNotNull(findDataBasedOnRegId);
	}
	
	@Test
	public void findDataBasedOnRegIdNullTest() {
		candidateSearch.setBdmReqId(null);
		candidateSearch.setRegistrationId(null);
		candidateSearch.setCandidateId(null);
		candidateSearch.setCandidateName(null);
		when(candidatelistDao.findAllCandidateBasedOnRegId(candidateSearch)).thenReturn(response);
		Response findDataBasedOnRegId = candidateResource.findDataBasedOnRegId(candidateSearch);
		ExceptionMessage status = findDataBasedOnRegId.getStatus();
		assertEquals(ExceptionMessage.Bad_Request, status);
	}
	
	
}
