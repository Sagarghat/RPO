package rpo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IntMapper;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.rest.resources.InterviewDetailsResource;
import com.ojas.rpo.security.service.InterviewDetailsService;
import com.ojas.rpo.security.util.InterviewDetailsValidation;
import com.ojas.rpo.security.util.InterviewSearch;

public class InterviewDetailsresourceTest {

	@InjectMocks
	private InterviewDetailsResource interviewDetailsResource;

	@Mock
	private InterviewDetailsService interviewDetailsService;

	@Mock
	private InterviewDetailsDao interviewDetailsDao;

	@Mock
	private InterviewDetailsValidation interviewValidation;

	@Spy
	Response response = new Response();

	@Spy
	InterviewDetails interviewDetails = new InterviewDetails();

	@Spy
	IntMapper intmapper = new IntMapper();

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveTest() {
		List<InterviewDetails> list = new ArrayList<InterviewDetails>();
		interviewDetails.setId(1L);
		interviewDetails.setInterviewLocation("Hyderabad");
		list.add(interviewDetails);

		intmapper.setDetails(any());
		response.setStatus(ExceptionMessage.StatusSuccess);
		// PowerMockito.mockStatic(InterviewDetailsValidation.class);
		when(interviewValidation.validateIinterview(intmapper)).thenReturn(response);
		when(interviewDetailsService.createInterview(intmapper)).thenReturn(response);
		Response save = interviewDetailsResource.save(intmapper);
		ExceptionMessage status = save.getStatus();
		assertNotNull(status);
	}

	@Test
	public void getTest() {
		interviewDetails.setId(1L);
		interviewDetails.setInterviewLocation("Hyderabad");
		intmapper.setDetails(interviewDetails);
		when(interviewDetailsService.get(interviewDetails.getId())).thenReturn(intmapper);
		Response response2 = interviewDetailsResource.get(interviewDetails.getId());
		ExceptionMessage status = response2.getStatus();
		assertNotNull(status);
	}

	@Test
	public void getNullTest() { 
		interviewDetails.setId(null);
		interviewDetails.setInterviewLocation(null);
		intmapper.setDetails(interviewDetails);
		when(interviewDetailsService.get(interviewDetails.getId())).thenReturn(intmapper);
		Response response2 = interviewDetailsResource.get(anyLong());
		ExceptionMessage status = response2.getStatus();
		assertEquals(ExceptionMessage.StatusSuccess, status);
	}

	@Test
	public void searchTest() {
		response.setStatus(ExceptionMessage.StatusSuccess);
		InterviewSearch interviewSearch = new InterviewSearch();
		interviewSearch.setInterviewName("Test");
		interviewSearch.setEmail("Example@gmail.com");
		interviewSearch.setMobile("324242443");
		when(interviewDetailsDao.findDataBasedOnRegId(interviewSearch)).thenReturn(response);
		Response search = interviewDetailsResource.search(interviewSearch);
		assertEquals(response,search);
	}
	
	@Test
	public void searchNullTest() {
		response.setStatus(ExceptionMessage.ExcepcetdDataNotAvilable);
		InterviewSearch interviewSearch = new InterviewSearch();
		interviewSearch.setInterviewName(null);
		interviewSearch.setEmail(null);
		interviewSearch.setMobile(null);
		when(interviewDetailsDao.findDataBasedOnRegId(interviewSearch)).thenReturn(response);
		Response search = interviewDetailsResource.search(interviewSearch);
		assertEquals(response,search);
	}
}
