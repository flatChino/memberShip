package com.example.membership.service;

import com.example.membership.DTO.MemberShipDTO;
import com.example.membership.constant.Role;
import com.example.membership.entity.MemberShip;
import com.example.membership.repository.MemberShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberShipService implements UserDetailsService {

    private final MemberShipRepository memberShipRepository;
    private  final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    // 회원가입 : 컨트롤러에서 dto를 입력받아 entity로 변환하여 repository의
    // save를 이용해서 저장한다.
    // 반환값은 뭐로 하까?? : dto전체를 반환으로 하자
    public MemberShipDTO saveMember(MemberShipDTO memberShipDTO) {


        MemberShip memberShip = memberShipRepository.findByEmail(memberShipDTO.getEmail());

        if(memberShip!=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        //일반 유져


        memberShip=
        modelMapper.map(memberShipDTO, MemberShip.class);

        memberShip.setRole(Role.ADMIN);
        memberShip.setPassword(passwordEncoder.encode(memberShipDTO.getPassword()));

        memberShip = memberShipRepository.save(memberShip);

        return modelMapper.map(memberShip,MemberShipDTO.class);

    }
    //로그인 //userDet


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //사용자가 입력한 emaildm 가지고 db에서 검색한다.
        //그러면 email과 password를 포함한 password를 찾거나
        //email로 검색해서 가져온 데이터가 null이 아니라면 그안에 있는 password를 가지고
        //비교해서 맞다면 로그인한다.
        log.info("유저디테일 서비스 들어온 이메일"+email);
        MemberShip memberShip=
                this.memberShipRepository.findByEmail(email);
        //이메일로 검색해서 가져온 값이 없다면, 그러니까 회원가입이 되이있지 않다면
        //try/catch문으로 다른  화면으로 덜리던 메시지를 가지고 로그인장으로 보내던 컨트롤러 창에서 알아서 해주겠지
        //미래의 내가
        if(memberShip ==null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        log.info("현재 찾은 회원정보"+memberShip);
        //권한 처리
        String role= "";
        if("ADMIN".equals(memberShip.getRole().name())){
            log.info("관리자");
            role=Role.ADMIN.name();
        }else{
            log.info("일반 유져");
            role=Role.USER.name();
        }


        return User.builder()
                .username(memberShip.getName())
                .password(memberShip.getPassword())
                .roles(role)
                .build();
    }
}
