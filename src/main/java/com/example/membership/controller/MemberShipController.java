package com.example.membership.controller;


import com.example.membership.DTO.MemberShipDTO;
import com.example.membership.service.MemberShipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class MemberShipController {
    private  final MemberShipService memberShipService;




    @GetMapping("/singnup")
    public String singnup(MemberShipDTO memberShipDTO){
        //파라미터는 유효성 검사를 하면 다시 보내줄거라

        return "user/singnup";
    }


    @PostMapping("/singnup")
    public String singnup(@Valid MemberShipDTO memberShipDTO, RedirectAttributes redirectAttributes,
                          BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("컨트롤러_유효성검사로 들어온 membershipDTO :"+memberShipDTO);
            return  "user/singnup";
        }



        memberShipDTO=
        memberShipService.saveMember(memberShipDTO);

        redirectAttributes.addFlashAttribute("zzzzz");

        return "redriect/user/singnup";
    }


    @GetMapping("login")
    public String login(Principal principal){
        if(principal !=null){//prinipal 로그인이 되었을때 가지게 됩니다.
            //현재는 email을 username로 가졌기 때문에 로그인한 email을 가지고 있습니다.
            log.info("===========================");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("||"+principal.getName()+"||");
            log.info("===========================");

            //return "redirect:/board/list";
            //memberShipService.loadUserByUsername(principal.getName());

        }

        return "/user/login";
    }

}
