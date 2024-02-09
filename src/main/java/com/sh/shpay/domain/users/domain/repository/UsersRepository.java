package com.sh.shpay.domain.users.domain.repository;

import com.sh.shpay.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);


}
