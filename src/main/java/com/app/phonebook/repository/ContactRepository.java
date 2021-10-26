package com.app.phonebook.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.phonebook.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Serializable> {
	
	public List<Contact> findByActiveSw(String activeSw);

}
