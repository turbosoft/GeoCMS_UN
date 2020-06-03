package kr.co.turbosoft.geocms.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import kr.co.turbosoft.geocms.util.ImageExtract;
import kr.co.turbosoft.geocms.util.VideoEncoding;

@Controller
public class EncodingController {
	@RequestMapping(value = "/geoEncoding.do", method = RequestMethod.POST)
	public void geoEncoding(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String file_name = request.getParameter("filename");
		//�̹��� ����
		ImageExtract imageExtract = new ImageExtract();
		imageExtract.ImageExtractor(file_name);
		
		//�ڵ� ���ڵ� (1�� : ogg)
		VideoEncoding videoEncoding = new VideoEncoding();
		videoEncoding.convertToOgg(file_name);
	}
	
	@RequestMapping(value = "/setChangeLocale.do", method = RequestMethod.GET)
	public String setChangeLocale(HttpServletRequest request, HttpServletResponse response) {
		String locale = request.getParameter("locale");
		HttpSession session = request.getSession();
		Locale locales = null;
		Locale defLocale = Locale.getDefault();
		// 넘어온 파라미터에 ko가 있으면 Locale의 언어를 한국어로 바꿔준다.
		// 그렇지 않을 경우 영어로 설정한다.
		if (locale.matches("ko_KR")) {
			locales = Locale.KOREAN;
		} else {
			locales = Locale.ENGLISH;
		}
		  
		// 세션에 존재하는 Locale을 새로운 언어로 변경해준다.
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locales);
		// 해당 컨트롤러에게 요청을 보낸 주소로 돌아간다.
		String redirectURL = "redirect:" + request.getHeader("referer");
		return redirectURL;
	}
}
