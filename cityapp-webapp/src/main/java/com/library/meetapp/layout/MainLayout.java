package com.library.meetapp.layout;


import com.library.meetapp.artfun.books.BooksView;
import com.library.meetapp.artfun.games.GamesView;
import com.library.meetapp.artfun.movieseries.MoviesAndSeriesView;
import com.library.meetapp.artfun.music.MusicView;
import com.library.meetapp.entity.User;
import com.library.meetapp.finance.entrepreuner.EntrepreneurshipView;
import com.library.meetapp.finance.investment.InvestmentView;
import com.library.meetapp.finance.personalfinance.PersonalFinanceView;
import com.library.meetapp.main.view.MainView;
import com.library.meetapp.selfimp.careerdev.CareerDevelopmentView;
import com.library.meetapp.selfimp.motivation.MotivationView;
import com.library.meetapp.selfimp.timemanage.TimeManagementView;
import com.library.meetapp.service.NotificationService;
import com.library.meetapp.society.currentevent.CurrentEventView;
import com.library.meetapp.society.economy.EconomyView;
import com.library.meetapp.pattern.TopToolbar;
import com.library.meetapp.sport.exercise.ExerciseView;
import com.library.meetapp.sport.mental.MentalHealthView;
import com.library.meetapp.sport.nutrition.NutritionView;
import com.library.meetapp.technology.ai.AIView;
import com.library.meetapp.technology.hardware.HardwareView;
import com.library.meetapp.technology.mobile.MobileAppsView;
import com.library.meetapp.technology.netsecurity.InternetSecurityView;
import com.library.meetapp.technology.software.SoftwareDevelopmentView;
import com.library.meetapp.ui.component.SideMenuItem;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class MainLayout extends AppLayout {

    @Autowired
    private User currentUser;

    @Autowired
    private NotificationService notificationService;

    private HorizontalLayout header;
    private Scroller scroller;

    private VerticalLayout mainContent = new VerticalLayout();

    private TopToolbar topToolbar = new TopToolbar();

    private Div detailNotificationDrawer = new Div();

    private Div detailDiv;

    private Button logout;

    public MainLayout() {
        initHeader();

        Div menu = new Div();
        menu.setClassName("menu-wrapper");
        createMenu(menu);
        addToDrawer(menu);

        addToNavbar(header);

    }

    private void initHeader(){
        H1 mainTitle = new H1("ArticleHub");
        mainTitle.addClassName(LumoUtility.FontSize.LARGE);

        header = new HorizontalLayout(new DrawerToggle(),mainTitle, topToolbar);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(mainTitle);
        header.setWidthFull();
        header.addClassName(
                LumoUtility.Padding.Vertical.NONE
        );
        header.setClassName("main-content");


    }


    private void createMenu(Div menu) {
        menu.add(new SideMenuItem(VaadinIcon.HOME, "Anasayfa", MainView.class));

        SideMenuItem techno = new SideMenuItem(VaadinIcon.MOBILE_BROWSER, "Teknoloji", null);
        techno.addSubItem(new SideMenuItem("Yapay Zeka", AIView.class));
        techno.addSubItem(new SideMenuItem("Yazılım Geliştirme", SoftwareDevelopmentView.class));
        techno.addSubItem(new SideMenuItem("Donanım", HardwareView.class));
        techno.addSubItem(new SideMenuItem("Mobil Uygulamalar", MobileAppsView.class));
        techno.addSubItem(new SideMenuItem("İnternet Güvenliği", InternetSecurityView.class));

        menu.add(techno);
        menu.add(techno.getSubItems());

        SideMenuItem finance = new SideMenuItem(VaadinIcon.BUILDING, "İş ve Finans", null);
        finance.addSubItem(new SideMenuItem("Girişimcilik", EntrepreneurshipView.class));
        finance.addSubItem(new SideMenuItem("Kişisel Finans", PersonalFinanceView.class));
        finance.addSubItem(new SideMenuItem("Yatırım", InvestmentView.class));
        menu.add(finance);
        menu.add(finance.getSubItems());


        SideMenuItem selImp = new SideMenuItem(VaadinIcon.USER, "Kişisel Gelişim", null);
        selImp.addSubItem(new SideMenuItem("Motivasyon", MotivationView.class));
        selImp.addSubItem(new SideMenuItem("Zaman Yönetimi", TimeManagementView.class));
        selImp.addSubItem(new SideMenuItem("Kariyer Gelişimi", CareerDevelopmentView.class));

        menu.add(selImp);
        menu.add(selImp.getSubItems());

        SideMenuItem sport = new SideMenuItem(VaadinIcon.HEART, "Spor ve Sağlık", null);
        sport.addSubItem(new SideMenuItem("Egzersiz", ExerciseView.class));
        sport.addSubItem(new SideMenuItem("Beslenme", NutritionView.class));
        sport.addSubItem(new SideMenuItem("Mental Sağlık", MentalHealthView.class));

        menu.add(sport);
        menu.add(sport.getSubItems());

        SideMenuItem art = new SideMenuItem(VaadinIcon.PAINTBRUSH, "Sanat ve Eğlence", null);
        art.addSubItem(new SideMenuItem("Kitap", BooksView.class));
        art.addSubItem(new SideMenuItem("Oyun", GamesView.class));
        art.addSubItem(new SideMenuItem("Dizi & Film", MoviesAndSeriesView.class));
        art.addSubItem(new SideMenuItem("Müzik", MusicView.class));

        menu.add(art);
        menu.add(art.getSubItems());

        SideMenuItem polAndSociety = new SideMenuItem(VaadinIcon.GROUP, "Politika ve Toplum", null);
        polAndSociety.addSubItem(new SideMenuItem("Güncel Olaylar", CurrentEventView.class));
        polAndSociety.addSubItem(new SideMenuItem("Ekonomi", EconomyView.class));

        menu.add(polAndSociety);
        menu.add(polAndSociety.getSubItems());

    }






}
