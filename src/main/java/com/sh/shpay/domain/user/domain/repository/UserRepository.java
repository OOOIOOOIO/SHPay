package com.sh.shpay.domain.user.domain.repository;

import com.sh.shpay.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
