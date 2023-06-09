package kr.ac.jipark09.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.ac.jipark09.dao.UserDao;
import kr.ac.jipark09.domain.User;
import kr.ac.jipark09.valid.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserDao userDao;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
        //	List<Validator> validatorList = binder.getValidators();
        //	System.out.println("validatorList="+validatorList);
    }

    @GetMapping("/add")
    public String register(HttpSession session) {
        session.invalidate();
        return "registerForm";
    }

    @PostMapping("/add")
    // 날짜 변환을 알아보지 못하기 때문에 Controller에서 에러를 받을려면 BindingResult를 써야 한다.
    // 검증할 대상에 @Valid를 붙여서 자동검증 해주게 한다.
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result="+result);
        System.out.println("user="+user);

        // 1. 수동 검증: Validator를 직접 생성하고, validate()를 직접 호출
//        UserValidator uservalidator = new UserValidator();
//        uservalidator.validate(user, result);// BindingResult가 Errors의 자손이니까 그대로 집어넣어줘도 됨

        // rselut에 검증결과가 담겨져있는데 error를 가지고 있으면 true반환
        if(result.hasErrors()) {
            return "registerForm";
        }

        // 2. DB에 신규회원 정보를 저장
        userDao.insertUser(user);
        return "index";
    }

    private boolean isValid(User user) {
        return true;
    }
}