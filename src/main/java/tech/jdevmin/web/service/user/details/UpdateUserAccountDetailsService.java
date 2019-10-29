package tech.jdevmin.web.service.user.details;

import tech.jdevmin.web.jpa.user.root.UserAccountDetails;
import tech.jdevmin.web.service.user.registration.UserAccountDetailsDTO;

public interface UpdateUserAccountDetailsService {

    UserAccountDetails updateAccountDetails(UserAccountDetailsDTO userAccountDetailsDTO);
}
