package com.example.membership.entity;

import com.example.membership.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class MemberShip {

    @Id                         //            제약조건 : 기본키(PK)
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(nullable = false)
    private String name;        // 이름       제약조건 : not null

    @Column(unique = true, nullable = false)
    private String email;       // 이메일      제약조건 : 유니크, not null

    @Column(nullable = false)
    private String password;    // 비밀번호     제약조건 : not null

    @Column(nullable = false)
    private String address;     // 주소        제약조건 : not null


    // 권한
    // 테이블로 만들경우 유동적으로 변경가능 or 테이블로 만들지 않을경우 enum으로 상수로 고정
    @Enumerated(EnumType.STRING)
    private Role role;


}
