package bootcamp.bankApi.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class HibernateSessionFactoryUtilTest {

    @Test
    public void getSessionFactory() {
        assertEquals(HibernateSessionFactoryUtil.getSessionFactory().isOpen(), true);
    }
}