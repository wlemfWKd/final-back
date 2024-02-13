package com.web.service;

import java.io.File;
import java.security.SecureRandom;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.domain.CheckMemberEmail;
import com.web.domain.Member;
import com.web.domain.Role;
import com.web.dto.JoinDTO;
import com.web.repository.CheckMemberEmailRepository;
import com.web.repository.MemberRepository;
@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private CheckMemberEmailRepository checkMemberEmailRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	public MemberServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	// 아이디 중복확인
	@Override
	public String checkId(String username) {
		// TODO Auto-generated method stub
		if(username.equals("")) {
			return "Empty";
		}
		// 해당 아이디로 검색했을 때 있으면 true 반환
		Boolean result = memberRepository.existsByUsername(username);
		if(result) {
			return "Exist";
		}
		return "Can Use";
	}
	
	// 이메일 중복확인
	@Override
	public String checkEmail(JoinDTO joinDTO) {
		// TODO Auto-generated method stub
		String email ="";
		if(joinDTO.getDomain().equals("")) {
			email = joinDTO.getEmail();
		}
		email = joinDTO.getEmail() + joinDTO.getDomain();
		System.out.println(email);
		if(joinDTO.getEmail().equals("")) {
			return "Empty";
		}
		// 해당 아이디로 검색했을 때 있으면 true 반환
		Boolean result = memberRepository.existsByEmail(email);
		if(result) {
			return "Exist";
		}
		return "Can Use";
	}

	// 이메일 인증 
	@Override
	public String checkMemberEmail(JoinDTO joinDTO) {
		// TODO Auto-generated method stub
		String email ="";
		String answer ="";
		if(joinDTO.getDomain().equals("직접입력")) {
			email = joinDTO.getEmail();
		} else {
			email = joinDTO.getEmail() + joinDTO.getDomain();
		}
		SimpleMailMessage message = new SimpleMailMessage();
		String randomInitial = generateRandomString(6);
		try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject("별책빵 회원가입 이메일 인증");
            helper.setTo(email);

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("회원가입을 진행해주셔서 감사합니다.\r\n");
            emailContent.append("이메일 인증을 위해 아래의 코드를.\r\n");
            emailContent.append("회원가입 페이지 코드 입력칸에 입력해주세요.").append("\n");
            emailContent.append("코드 : "+randomInitial).append("\n");
            MimeMultipart multipart = new MimeMultipart();
            // 텍스트 내용 추가
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(emailContent.toString());
            multipart.addBodyPart(textBodyPart);
            mimeMessage.setContent(multipart);
            javaMailSender.send(mimeMessage);
            CheckMemberEmail checkMemberEmail = new CheckMemberEmail();
            checkMemberEmail.setEmail(email);
            checkMemberEmail.setRandomInitial(randomInitial);
            checkMemberEmailRepository.save(checkMemberEmail);
            
            answer = "Success";
        } catch (MessagingException e) {
            e.printStackTrace();
            answer = "Failure";
        }
		return answer;
	}
	
	// 이메일 인증 코드
	@Override
	public String checkCode(JoinDTO joinDTO) {
		// TODO Auto-generated method stub
		String email ="";
		if(joinDTO.getDomain().equals("직접입력")) {
			email = joinDTO.getEmail();
		} else {
			email = joinDTO.getEmail() + joinDTO.getDomain();
		}
		Optional<CheckMemberEmail> optional = checkMemberEmailRepository.findById(email);
		CheckMemberEmail checkMemberEmail = null;
		if(optional.isPresent()) {
			checkMemberEmail = optional.get();
			if(joinDTO.getRandomInitial().equals(checkMemberEmail.getRandomInitial())) {
				checkMemberEmailRepository.deleteById(email);
				return "Success";
			}
			return "Failure";
		}
		return "Failure";
	}

	// 회원가입
	@Override
	public String join(JoinDTO joinDTO) {
		// TODO Auto-generated method stub
		Member member = Member.builder()
				.memberName(joinDTO.getMemberName())
				.username(joinDTO.getUsername())
				.email(joinDTO.getEmail()+joinDTO.getDomain())
				.password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
				.phoneNum(joinDTO.getPhoneNum())
				.socialNum(joinDTO.getSocialNum1()+joinDTO.getSocialNum2())
				.membership("일반 회원")
				.role(Role.USER)
				.build();
		memberRepository.save(member);
		return "ok";
	}
	// 필요할 때 토큰 넘겨서 username(아이디)로 멤버 정보 불러오기
	@Override
	public Member getMemberInfo(String username) {
		// TODO Auto-generated method stub
		Member member = memberRepository.findByUsername(username);
		if(member != null) {
			// 아이디값과 일치하는 멤버객체를 반환
			return member; 
		}
		return null;
	}
	
}
