package ragen.common.interceptor;

import ragen.common.util.LoginUser;
import ragen.common.util.SessionConst;
import ragen.common.util.SessionUtil;
import ragen.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class HttpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute(SessionConst.TOKEN) == null) {
//            SessionUtil.reponseBody(response);
//            return false;
//        }
//
//        String sessionToken = (String) session.getAttribute(SessionConst.TOKEN);
//        String headToken = request.getHeader(SessionConst.TOKEN);
//
//        if (!StringUtils.equals(sessionToken, headToken)) {
//            SessionUtil.reponseBody(response);
//
//            if (session != null) {
//                session.invalidate();
//            }
//
//            return false;
//        }
//
//        LoginUser loginUser = new LoginUser(
//                (String) session.getAttribute(SessionConst.USRID),
//                (String) session.getAttribute(SessionConst.AUTHCD),
//                (String) session.getAttribute(SessionConst.TOKEN),
//                (String) session.getAttribute(SessionConst.CONNIP)
//        );
//
//        request.setAttribute("LoginUser", loginUser);
//
       return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 요청 처리 후에 수행할 로직을 구현합니다.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        // 요청 완료 후에 수행할 로직을 구현합니다.
    }
}
