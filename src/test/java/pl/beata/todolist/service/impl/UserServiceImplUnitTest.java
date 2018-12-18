package pl.beata.todolist.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import pl.beata.todolist.dao.UserDao;
import pl.beata.todolist.model.User;
import pl.beata.todolist.service.SessionService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplUnitTest {

	private UserServiceImpl userService;
	private UserDao userDao;
	private SessionService sessionService;

	@Before
	public void createServiceObject() {
		userDao = Mockito.mock(UserDao.class);
		sessionService = Mockito.mock(SessionService.class);
		userService = new UserServiceImpl(userDao, sessionService);
	}

	@Test
	public void shouldLogin() {
		// given
		String email = "lala@gmail.com";
		String password = "abc";
		Integer id = 5;
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setId(id);

		Mockito.when(userDao.findUserByEmail(email)).thenReturn(user);

		// when
		boolean loginSuccessful = userService.login(email, password);

		// then
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		Mockito.verify(sessionService).setCurrentUserId(captor.capture());
		Integer currentUserId = captor.getValue();

		Assert.assertEquals(true, loginSuccessful);
		Assert.assertEquals(id, currentUserId);
	}

	@Test
	public void shouldNotLoginPasswordIsWrong() {
		// given
		String email = "lala@gmail.com";
		String rightPassword = "abc";
		String wrongPassword = "xyz";
		User user = new User();
		user.setEmail(email);
		user.setPassword(rightPassword);

		Mockito.when(userDao.findUserByEmail(email)).thenReturn(user);

		// when

		boolean loginSuccessful = userService.login(email, wrongPassword);

		// then

		Mockito.verify(sessionService, Mockito.never()).setCurrentUserId(Mockito.any());

		Assert.assertEquals(false, loginSuccessful);
	}

	@Test
	public void shouldNotLoginUserIsNull() {
		// given
		String email = "lala@gmail.com";
		String password = "abc";

		Mockito.when(userDao.findUserByEmail(email)).thenReturn(null);

		// when

		boolean loginSuccessful = userService.login(email, password);
		// then

		Mockito.verify(sessionService, Mockito.never()).setCurrentUserId(Mockito.any());

		Assert.assertEquals(false, loginSuccessful);
	}

}
