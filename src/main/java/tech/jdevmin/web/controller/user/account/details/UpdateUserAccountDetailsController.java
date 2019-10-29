package tech.jdevmin.web.controller.user.account.details;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.service.user.registration.UserAccountDetailsDTO;
import tech.jdevmin.web.service.user.details.UpdateUserAccountDetailsService;

@Controller
@RequestMapping("/user/account/details")
@Slf4j
public class UpdateUserAccountDetailsController {

    @Autowired
    private UpdateUserAccountDetailsService updateUserAccountDetailsService;

    @GetMapping
    public String getUpdateForm(Model model){
        model.addAttribute("user", new UserAccountDetailsDTO());

        return "mobile/user/account/UpdateUserAccountDetailsForm";
    }

    @PostMapping("/update")
    public String updateUserAccount(@ModelAttribute UserAccountDetailsDTO userAccountDetailsDTO){

        log.info("update user account endpoint triggered");
        log.info(userAccountDetailsDTO.toString());
        updateUserAccountDetailsService.updateAccountDetails(userAccountDetailsDTO);

        return "redirect:/home";
    }
}
