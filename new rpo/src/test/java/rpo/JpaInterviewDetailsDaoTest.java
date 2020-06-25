package rpo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.InterviewDetails.JpaInterviewDetailsDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IntMapper;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.InterviewSearch;

public class JpaInterviewDetailsDaoTest {
	
	@InjectMocks
	private JpaInterviewDetailsDao japDao;
	
	@Mock
	private InterviewDetailsDao interviewDetailsDao;
	
	@Mock
	InterviewDetails interviewDetails = new InterviewDetails();
	
	@Mock 
	InterviewSearch interviewSearch = new InterviewSearch();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getTest() {
		interviewDetails.setId(21L);
		//interviewDetails.setInterviewLocation("Mumbai");
		IntMapper intMapper = new IntMapper();
		intMapper.setDetails(interviewDetails);
		//when(interviewDetails.getId()).thenReturn(21L);
		InterviewDetails find = interviewDetailsDao.find(interviewDetails.getId());
		assertNull(find);
	}
	
	@Test
	public void getNullTest() {
		interviewDetails.setId(null);
		//interviewDetails.setInterviewLocation("Mumbai");
		IntMapper intMapper = new IntMapper();
		intMapper.setDetails(interviewDetails);
		//when(interviewDetails.getId()).thenReturn(21L);
		InterviewDetails find = interviewDetailsDao.find(interviewDetails.getId());
		assertNull(find);
	}
	
	@Test
	public void findDataBasedOnRegIdTest() {
		interviewSearch.setEmail("Email@gmail.com");
		interviewSearch.setClientId(12L);
		Response findDataBasedOnRegId = japDao.findDataBasedOnRegId(interviewSearch);
		ExceptionMessage status = findDataBasedOnRegId.getStatus();
		assertEquals(ExceptionMessage.StatusSuccess,status);
		}
}
