package com.library.meetapp.navigation.ui;

import com.library.meetapp.artfun.books.BookDetailView;
import com.library.meetapp.artfun.games.GameDetailView;
import com.library.meetapp.artfun.movieseries.MovieSeriesDetailView;
import com.library.meetapp.artfun.music.MusicDetailView;
import com.library.meetapp.entity.Post;
import com.library.meetapp.finance.entrepreuner.EntrepreneurshipDetailView;
import com.library.meetapp.finance.investment.InvestmentDetailView;
import com.library.meetapp.finance.personalfinance.PersonalFinanceDetailView;
import com.library.meetapp.selfimp.careerdev.CareerDevDetailView;
import com.library.meetapp.selfimp.careerdev.CareerDevelopmentView;
import com.library.meetapp.selfimp.motivation.MotivationDetailView;
import com.library.meetapp.selfimp.timemanage.TimeManageDetailView;
import com.library.meetapp.society.currentevent.CurrentEventDetailView;
import com.library.meetapp.society.economy.EconomyDetailView;
import com.library.meetapp.sport.exercise.ExerciseDetailView;
import com.library.meetapp.sport.mental.MentalHealthDetailView;
import com.library.meetapp.sport.nutrition.NutritionDetailView;
import com.library.meetapp.technology.ai.AIDetailView;
import com.library.meetapp.technology.hardware.HardwareDetailView;
import com.library.meetapp.technology.mobile.MobileAppsDetailView;
import com.library.meetapp.technology.netsecurity.InternetDetailView;
import com.library.meetapp.technology.software.SoftwareDevelopmentDetailView;
import com.vaadin.flow.component.UI;

public class PostUtil {


    public static void navigateToPostDetail(UI ui, Post post) {
        switch (post.getCategory()) {
            case "ENT":
                ui.navigate(EntrepreneurshipDetailView.class, post.getId());
                break;
            case "INV":
                ui.navigate(InvestmentDetailView.class, post.getId());
                break;
            case "PF":
                ui.navigate(PersonalFinanceDetailView.class, post.getId());
                break;
            case "AI":
                ui.navigate(AIDetailView.class, post.getId());
            case "SD":
                ui.navigate(SoftwareDevelopmentDetailView.class, post.getId());
            case "IS":
                ui.navigate(InternetDetailView.class, post.getId());
            case "HW":
                ui.navigate(HardwareDetailView.class, post.getId());
            case "MA":
                ui.navigate(MobileAppsDetailView.class, post.getId());
            case "NUT":
                ui.navigate(NutritionDetailView.class, post.getId());
            case "MH":
                ui.navigate(MentalHealthDetailView.class, post.getId());
            case "EXE":
                ui.navigate(ExerciseDetailView.class, post.getId());
            case "CURRENT_EVENTS":
                ui.navigate(CurrentEventDetailView.class, post.getId());
            case "ECONOMY":
                ui.navigate(EconomyDetailView.class, post.getId());
            case "CD":
                ui.navigate(CareerDevDetailView.class, post.getId());
            case "MOT":
                ui.navigate(MotivationDetailView.class, post.getId());
            case "TIME":
                ui.navigate(TimeManageDetailView.class, post.getId());
            case "BOOK":
                ui.navigate(BookDetailView.class, post.getId());
            case "GAMES":
                ui.navigate(GameDetailView.class, post.getId());
            case "MOVIES":
                ui.navigate(MovieSeriesDetailView.class, post.getId());
            case "MUSIC":
                ui.navigate(MusicDetailView.class, post.getId());

            default:
                break;
        }
    }












}
