package com.Jguides.spingboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Jguides.spingboot.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
