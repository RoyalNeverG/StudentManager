package com.lc.studentmanager.permissionManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.permissionManager
 * @Author: lc
 * @CreateTime: 2019-12-14 14:39
 * @Description:
 */
@Component
@Aspect
public class AuthTokenAspect {

    /**
            * Spring中使用@Pointcut注解来定义方法切入点
	 * @Pointcut
    用来定义切点，针对方法  @Aspect 用来定义切面，针对类
	 * 后面的增强均是围绕此切入点来完成的
	 * 此处仅配置被我们刚才定义的注解：AuthToken修饰的方法即可
	 *
             */
    @Pointcut("@annotation(authToken)")
    public void doAuthToken(AuthToken authToken) {
    }

    /**
     * 此处我使用环绕增强，在方法执行之前或者执行之后均会执行。
     */
    @Around("doAuthToken(authToken)")
    public Object deBefore(ProceedingJoinPoint pjp, AuthToken authToken) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
//        获取response对象
        HttpServletResponse response = servletRequestAttributes.getResponse();
        HttpSession session = request.getSession();
        if (session.getAttribute("role") == null) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("<script>alert('请先登录再操作！');</script>");
            response.getWriter().flush();
            return "login";
        }
        //获取访问该方法的信息
        String[] role_name = authToken.role_name();

        if (role_name == null || role_name.length == 0) {
            //没有角色限制 直接访问
            return pjp.proceed();
        } else {
            for (String s : role_name) {
                if (s.equals(session.getAttribute("role").toString()) || s.equals("")) {
                    return pjp.proceed();
                }
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("<script>alert('您没有此权限！');</script>");
            response.getWriter().flush();
            pjp.proceed();
            String pagename = (String) request.getAttribute("pagename");
            System.out.println("---==-=-=-" + pagename);
//            if (pagename == null) {
//                return "redirect:/info";
//            }
//            if (pagename.equals("course")) {
//                return "redirect:/course/findall/1";
//            }
            return "userinfo";
        }


    }
}
