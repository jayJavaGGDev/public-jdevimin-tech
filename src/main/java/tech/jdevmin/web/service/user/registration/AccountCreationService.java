package tech.jdevmin.web.service.user.registration;

import tech.jdevmin.web.jpa.user.root.UserRoot;

public interface AccountCreationService {

    UserRoot createNewAccount(UserAccountDetailsDTO accountDetailsDTO);
    void createNewTestAccounts();
}
