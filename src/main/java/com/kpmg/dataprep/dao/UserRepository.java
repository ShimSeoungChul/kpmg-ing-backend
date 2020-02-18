package com.kpmg.dataprep.dao;

import com.kpmg.dataprep.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
