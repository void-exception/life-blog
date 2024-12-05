package com.example.demo.Controller;

import com.example.demo.Model.Blog;
import com.example.demo.Model.BlogRepository;
import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private MyAppUserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);
        return "home";
    }

    @GetMapping("/create")
    public String blogCreate(Model model) {
        return "blogCreate";
    }

    //    @GetMapping("/details/{id}")
//    public String blogDetails(@PathVariable(value = "id") long id,  Model model){
//
//        Optional<Blog> blog = blogRepository.findById(id);
//        ArrayList<Blog> res = new ArrayList<>();
//        blog.ifPresent(res::add);
//        model.addAttribute("blog", res);
//        return "blogDetails";
//    }
    @GetMapping("/details/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Blog> blog = blogRepository.findById(id);
        model.addAttribute("blog", blog.get());
        return "blogDetails"; // Страница с полными деталями
    }



    @GetMapping("/view/details/{id}")
    public String blogViewDetails(@PathVariable(value = "id") long id, Model model) {
        Optional<Blog> blog = blogRepository.findById(id);
        model.addAttribute("blog", blog.get());  // Передаем конкретный пост в модель
        return "blogDetailsView";
    }

    @GetMapping("/edit/{id}")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Blog> blog = blogRepository.findById(id);
        ArrayList<Blog> res = new ArrayList<>();
        blog.ifPresent(res::add);
        model.addAttribute("blog", res);
        return "blogEdit";
    }


    @PostMapping("/create")
    public String blogPostCreate(@RequestParam String title, @RequestParam String summary, @RequestParam String fullText, @AuthenticationPrincipal MyAppUser currentUser, Model model) {
        String author = currentUser.getUsername();
        Blog blog = new Blog(title, summary, fullText, author);
        blogRepository.save(blog);
        return "redirect:/home";
    }

    @PostMapping("/edit/{id}")
    public String blogPostEdit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String summary, @RequestParam String fullText, /*@AuthenticationPrincipal MyAppUser currentUser,*/ Model model) {
        Blog blog = blogRepository.findById(id).orElseThrow();
        blog.setTitle(title);
        blog.setSummary(summary);
        blog.setFullText(fullText);
        blogRepository.save(blog);
        return "redirect:/home";
    }

    @PostMapping("/delete/{id}")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
//        Blog blog = blogRepository.findById(id).orElseThrow();
        blogRepository.deleteById(id);
        return "redirect:/home";
    }

    @PostMapping("/view/details/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, @AuthenticationPrincipal MyAppUser currentUser, Model model) {
        Optional<Blog> blog = blogRepository.findById(id);
        blog.get().setViews(blog.get().getViews()+1);
        blogRepository.save(blog.get());


            // Если автор, перенаправляем на страницу с полными деталями
            if (currentUser.getUsername().equals(blog.get().getAuthor())) {
                return "redirect:/details/" + id;
            }
            // Если не автор, показываем страницу с ограниченным доступом
            model.addAttribute("blog", blog.get());
            return "blogDetailsView";

    }
}