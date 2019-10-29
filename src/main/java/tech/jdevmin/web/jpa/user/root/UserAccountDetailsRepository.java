package tech.jdevmin.web.jpa.user.root;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAccountDetailsRepository extends CrudRepository<UserAccountDetails,Long> {

    UserAccountDetails findByUsername(String username);

    @Override
    Optional<UserAccountDetails> findById(Long aLong);


}
