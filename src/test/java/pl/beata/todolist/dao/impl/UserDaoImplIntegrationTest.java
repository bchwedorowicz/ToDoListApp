package pl.beata.todolist.dao.impl;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.beata.todolist.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureTestEntityManager
@Transactional
public class UserDaoImplIntegrationTest {
	
	@Autowired
	private UserDaoImpl userDao;
	@Autowired
	private TestEntityManager testEm;
	
	@Test
	public void shouldFindUserByEmail() {
		//given
		String email1 = "abc@gmail.com";
		String email2 = "xyz@gmail.com";
		
		int id1 = 1;
		int id2 = 2;
		
		createUser(id1, email1);
		createUser(id2, email2);
		
		//when
		
		User actualUser1 = userDao.findUserByEmail(email1);
		
		User actualUser2 = userDao.findUserByEmail(email2);
		
		User actualUser3 = userDao.findUserByEmail("lala@gmail.com");
		
		//then
		Assert.assertEquals(id1, actualUser1.getId());
		Assert.assertEquals(id2, actualUser2.getId());
		Assert.assertNull(actualUser3);
	}
	
	private void createUser(int id, String email) {
		String insert = "INSERT INTO User (id, email) VALUES (:id, :email)";
		Query query = testEm.getEntityManager().createNativeQuery(insert);
		query.setParameter("id", id);
		query.setParameter("email", email);
		query.executeUpdate();
	}

}
