package slipp.controller;

import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.dao.UserDao;
import slipp.dto.UserUpdatedDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);

    private final UserDao userDao = new UserDao();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        return this.userDao.findByUserId(req.getParameter("userId")).filter(user ->
            UserSessionUtils.isSameUser(req.getSession(), user)
        ).map(user -> {
            final UserUpdatedDto updateUser = new UserUpdatedDto(
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email")
            );
            logger.debug("Update User : {}", updateUser);
            this.userDao.update(user.update(updateUser));
            return "redirect:/";
        }).orElseThrow(() -> new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
    }
}