package org.tree.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tree.commons.annotation.Ignore;
import org.tree.commons.support.controller.Result;
import org.tree.commons.support.mapper.MapperTemplate;
import org.tree.commons.support.service.message.AuthCodeService;
import org.tree.commons.utils.VerifyUtils;
import org.tree.commons.utils.auth.AuthCodePicture;
import org.tree.learning.model.Learner;
import org.tree.learning.model.LearnerExample;
import org.tree.learning.vo.in.LoginVO;
import org.tree.learning.vo.in.LoginVO2;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * @author er_dong_chen
 * @date 2019/1/14
 */
@RestController
@RequestMapping("/learner")
public class LearnerController {
    @Autowired(required = false)
    private AuthCodeService authCodeService;
    @Autowired
    private MapperTemplate mapper;
    @Autowired
    private HttpSession session;

    private Set<String> pictureCode = new HashSet<>(30);

    @RequestMapping("/isLogin.do")
    public Result isLogin() {
        return new Result(true);
    }

    @RequestMapping("/login.do")
    public Result login(@Valid LoginVO loginVO, @Ignore BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Result(false, VerifyUtils.getErrorString(bindingResult));

        if (!pictureCode.remove(loginVO.getCode()))
            return new Result(false, "验证码错误");

        LearnerExample example = new LearnerExample();
        example.createCriteria().
                andUsernameEqualTo(loginVO.getUsername()).
                andPasswordEqualTo(loginVO.getPassword());
        Learner learner = mapper.selectByExampleForObject(example);

        Result result = new Result();
        if (learner != null) {
            session.setAttribute("login", true);
            session.setAttribute("admin", learner.getAdmin());
            result.setSuccess(true);
        } else {
            result.setMessage("账号或密码错误");
        }
        return result;
    }

    @RequestMapping("/login2.do")
    public Result login2(@Valid LoginVO2 loginVO2, @Ignore BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Result(false, VerifyUtils.getErrorString(bindingResult));

        if (authCodeService.confirmAuthCode(loginVO2.getUsername(), loginVO2.getCode())) {
            LearnerExample example = new LearnerExample();
            example.createCriteria().andUsernameEqualTo(loginVO2.getUsername());
            return new Result(mapper.countByExample(example) == 1);
        }

        return new Result();
    }

    @RequestMapping("/logout.do")
    public Result logout() {
        session.invalidate();
        return new Result(true);
    }

    @RequestMapping("/register.do")
    public Result register(@Valid LoginVO loginVO, @Ignore BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new Result(false, VerifyUtils.getErrorString(bindingResult));

        Learner learner = new Learner();
        learner.setUsername(loginVO.getUsername());
        learner.setPassword(loginVO.getPassword());
        return new Result(mapper.insertSelective(learner) == 1);
    }

    @RequestMapping("/getPictureCode.do")
    public void getPictureCode(@Ignore HttpServletResponse response) throws Exception {
        String code = AuthCodePicture.generate(response.getOutputStream());
        pictureCode.add(code);
    }

    @RequestMapping("/sendAuthCode.do")
    public Result sendAuthCode(String to) {
        return new Result(authCodeService.sendAuthCode(to));
    }
}
