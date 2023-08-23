package com.example.blog_springboot.modules.comment.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    public CommentController(){

    }

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String abc(){
        logger.info("Logger comment");
        return "ngu";
    }
}
