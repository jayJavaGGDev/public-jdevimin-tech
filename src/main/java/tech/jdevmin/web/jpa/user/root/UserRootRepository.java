package tech.jdevmin.web.jpa.user.root;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRootRepository extends CrudRepository<UserRoot, Long> {


    UserRoot findByUserRootId(long id);

//    UserRoot findById(long id);

    UserRoot findByIdentity(String username);

    List<UserRoot> findAll();


}
