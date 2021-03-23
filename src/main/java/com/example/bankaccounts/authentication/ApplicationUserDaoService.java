package com.example.bankaccounts.authentication;

import com.example.bankaccounts.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("users")
public class ApplicationUserDaoService implements ApplicationUserDao {

    private final UsersRepository usersRepository;

    @Autowired
    public ApplicationUserDaoService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return Optional.ofNullable(new ApplicationUser(usersRepository.findByUsername(username)));
    }
}
