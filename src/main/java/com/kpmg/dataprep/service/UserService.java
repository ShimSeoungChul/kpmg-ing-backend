package com.kpmg.dataprep.service;

import com.kpmg.dataprep.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    String tag = "UserService";

    @Autowired
    UserRepository userRepository;

    public int getUser(String id) {
        return userRepository.getOne(id).getTotThroughput();
    }



}
