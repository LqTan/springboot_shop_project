package vn.melowyeti.spring.spring_ecommerce_project.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Blob;

@Service
public class LobHelper {
    private final SessionFactory sessionFactory;
    @Autowired
    public LobHelper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Blob createBlob(InputStream content, long size) {
        return sessionFactory.getCurrentSession().getLobHelper().createBlob(content, size);
    }
}
