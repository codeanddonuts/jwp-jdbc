package slipp.controller;

import nextstep.mvc.asis.Controller;
import slipp.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormUserController implements Controller {
    private final UserDao userDao;

    public UpdateFormUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return this.userDao.findByUserId(req.getParameter("userId")).filter(user ->
            UserSessionUtils.isSameUser(req.getSession(), user)
        ).map(user -> {
            req.setAttribute("user", user);
            return "/user/updateForm.jsp";
        }).orElseThrow(() -> new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
    }
}