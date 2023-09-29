package com.example.blog_springboot.modules.comment.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/api/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    public CommentController(){

    }

    @ResponseBody
    @GetMapping("")
    public String abc(){
        logger.info("Logger comment");
        return "ngu";
    }
}
