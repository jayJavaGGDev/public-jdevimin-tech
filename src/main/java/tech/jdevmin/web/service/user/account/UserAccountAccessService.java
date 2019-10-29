package tech.jdevmin.web.service.user.account;

import tech.jdevmin.web.jpa.user.root.UserRoot;

public interface UserAccountAccessService {

    UserRoot accessAccount(String username);
    UserRoot accessAccount(long id);
}
