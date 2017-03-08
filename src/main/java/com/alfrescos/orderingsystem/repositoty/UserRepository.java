package com.alfrescos.orderingsystem.repositoty;


import com.alfrescos.orderingsystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Liger on 06-Mar-17.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findByAccountCode(String accountCode);

}
