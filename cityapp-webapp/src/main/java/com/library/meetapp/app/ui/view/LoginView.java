package com.library.meetapp.app.ui.view;


import com.library.meetapp.main.view.MainView;
import com.library.meetapp.security.SecurityUtils;
import com.library.meetapp.service.UserService;
import com.library.meetapp.util.UIUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements AfterNavigationObserver,BeforeEnterObserver {


    @Autowired
    private UserService userService;

    private final LoginForm loginForm = new LoginForm();
    private Span passwordStrengthText;

    private Button newUser = UIUtil.createPrimaryButton("Kayıt Ol");
    private Button turnLogin = UIUtil.createPrimaryButton("Giriş Yap");


    public LoginView() {
        setClassName("login-view");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        LoginForm loginForm = createLoginForm();

        add(loginForm, newUser);

        newUser.addClickListener(evt -> {
            removeAll();
            createRegisterForm();
            add(turnLogin);
        });

        turnLogin.addClickListener(evt -> {
           removeAll();
           LoginForm currentForm = createLoginForm();
           add(currentForm, newUser);
        });

        loginForm.getElement().setAttribute("no-autofocus", "");

    }

    private void createRegisterForm() {
        H2 title = new H2("Kayıt Ekranı");
        FormLayout registerForm = new FormLayout();
        TextField firstName = new TextField("Ad");
        TextField lastName = new TextField("Soyad");
        TextField userName = new TextField("Kullanıcı Adı");
        PasswordField passwordField = new PasswordField("Şifre");
        Button save = UIUtil.createPrimaryButton("Kaydet");


        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.addValueChangeListener(e -> {
            String password = e.getValue();
            updateHelper(password);
        });

        Div passwordStrength = new Div();
        passwordStrengthText = new Span();
        passwordStrength.add(new Text("Parola güvenliği: "),
                passwordStrengthText);
        passwordField.setHelperComponent(passwordStrength);

        registerForm.add(title,firstName, lastName,userName, passwordField, save);
        registerForm.setClassName("register-form");

        add(registerForm);

        save.addClickListener(evt -> {
            if(firstName != null && !firstName.isEmpty() & lastName != null && !lastName.isEmpty() & userName != null && !userName.isEmpty() && passwordField != null && !passwordField.isEmpty()) {
                userService.createUser(userName.getValue(),firstName.getValue(),lastName.getValue(),passwordField.getValue());
                changeView();
            } else {
                Notification.show("Lütfen bilgilerinizi eksiksiz giriniz", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
    }

    private void updateHelper(String password) {
        if (password.length() > 9) {
            passwordStrengthText.setText("güçlü");
            passwordStrengthText.getStyle().set("color",
                    "var(--lumo-success-color)");
        } else if (password.length() > 5) {
            passwordStrengthText.setText("orta");
            passwordStrengthText.getStyle().set("color", "#e7c200");
        } else {
            passwordStrengthText.setText("zayıf");
            passwordStrengthText.getStyle().set("color",
                    "var(--lumo-error-color)");
        }
    }


    private void changeView() {
        removeAll();
        add(createLoginForm(), newUser);
    }


    private LoginForm createLoginForm() {
        LoginForm loginForm = new LoginForm();
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("App");
        i18n.getForm().setSubmit("Giriş Yap");
        i18n.getForm().setTitle("Giriş Ekranı");
        i18n.getForm().setUsername("Kullanıcı Adı");
        i18n.getForm().setPassword("Şifre");
        i18n.getErrorMessage().setTitle("Giriş Başarısız! Tekrar deneyiniz");
        i18n.getErrorMessage().setMessage("Lütfen, kullanıcı adı ve şifreyi kontrol edip tekrar deneyin");


        loginForm.setI18n(i18n);
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);
        return  loginForm;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
//        if(beforeEnterEvent.getLocation()
//                .getQueryParameters()
//                .getParameters()
//                .containsKey("error")) {
//            loginForm.setError(true);
//        }
        if (SecurityUtils.isUserLoggedIn()) {
            event.forwardTo(MainView.class);
        }


    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
