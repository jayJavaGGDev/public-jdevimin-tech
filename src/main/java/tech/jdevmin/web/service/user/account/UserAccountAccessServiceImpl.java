package tech.jdevmin.web.service.user.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.jpa.user.root.UserRootRepository;

@Service
@Slf4j
public class UserAccountAccessServiceImpl implements UserAccountAccessService {

    @Autowired
    private UserRootRepository userRootRepository;

    @Override
    public UserRoot accessAccount(String username) {
        log.info("finding account by username {}",username);
        return userRootRepository.findByIdentity(username);
    }

    @Override
    public UserRoot accessAccount(long userRootId) {
        return userRootRepository.findById(userRootId).get();
    }
}
