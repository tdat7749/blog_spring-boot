package com.example.blog_springboot.modules.comment.service;


import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.comment.constant.CommentConstants;
import com.example.blog_springboot.modules.comment.dto.CreateCommentDTO;
import com.example.blog_springboot.modules.comment.dto.EditCommentDTO;
import com.example.blog_springboot.modules.comment.exception.CommentNotFoundException;
import com.example.blog_springboot.modules.comment.exception.CreateCommentException;
import com.example.blog_springboot.modules.comment.exception.NotAuthorCommentException;
import com.example.blog_springboot.modules.comment.exception.UpdateCommentException;
import com.example.blog_springboot.modules.comment.model.Comment;
import com.example.blog_springboot.modules.comment.repository.CommentRepository;
import com.example.blog_springboot.modules.comment.viewmodel.CommentVm;
import com.example.blog_springboot.modules.post.constant.PostConstants;
import com.example.blog_springboot.modules.post.exception.PostNotFoundException;
import com.example.blog_springboot.modules.post.repository.PostRepository;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Override
    public SuccessResponse<CommentVm> createComment(CreateCommentDTO dto,int postId, User user) {
        var foundPost = postRepository.findById(postId).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        Comment newComment = new Comment();
        newComment.setPost(foundPost);
        newComment.setContent(dto.getContent());
        newComment.setUser(user);
        newComment.setCreatedAt(new Date());
        newComment.setUpdatedAt(new Date());
        if(dto.getParentId() != null){
            newComment.setParentId(dto.getParentId());
        }

        var save = commentRepository.save(newComment);
        if(save == null){
            throw new CreateCommentException(CommentConstants.CREATE_COMMENT_FAILED);
        }
        var commentVm = getCommentVM(save);
        return new SuccessResponse<>(CommentConstants.CREATE_COMMENT_SUCCESS,commentVm);
    }

    @Override
    public SuccessResponse<CommentVm> editComment(EditCommentDTO dto,int postId, User user) {
        var isAuthor = commentRepository.existsByUserAndId(user,dto.getId());
        if(!isAuthor){
            throw new NotAuthorCommentException(CommentConstants.NOT_AUTHOR_COMMENT);
        }

        var foundComment = commentRepository.findById(dto.getId()).orElse(null);
        if(foundComment == null){
            throw new CommentNotFoundException(CommentConstants.COMMENT_NOT_FOUND);
        }

        foundComment.setContent(dto.getContent());

        var save = commentRepository.save(foundComment);

        if(save == null){
            throw new UpdateCommentException(CommentConstants.EDIT_COMMENT_FAILED);
        }

        var commentVm = getCommentVM(save);
        return new SuccessResponse<>(CommentConstants.EDIT_COMMENT_SUCCESS,commentVm);
    }

    @Override
    public SuccessResponse<Boolean> deleteComment(int id, User user) {
        var isAuthor = commentRepository.existsByUserAndId(user,id);
        if(!isAuthor){
            throw new NotAuthorCommentException(CommentConstants.NOT_AUTHOR_COMMENT);
        }

        var foundComment = commentRepository.findById(id).orElse(null);
        if(foundComment == null){
            throw new CommentNotFoundException(CommentConstants.COMMENT_NOT_FOUND);
        }

        commentRepository.delete(foundComment);

        return new SuccessResponse<>(CommentConstants.DELETE_COMMENT_SUCCESS,true);
    }

    @Override
    public SuccessResponse<PagingResponse<List<CommentVm>>> getListCommentPost(String slug,String sortBy, int pageIndex) {
        var foundPost = postRepository.findBySlug(slug).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        var pagingResult = commentRepository.getListCommentByPost(foundPost,paging);

        var listCommentVm = pagingResult.stream().map(this::getCommentVmWithChild).toList();

        return new SuccessResponse<>("Thành công!",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listCommentVm));

    }

    @Override
    public long countCommentPost(int postId) {
        var foundPost = postRepository.findById(postId).orElse(null);
        if(foundPost == null){
            throw new PostNotFoundException(PostConstants.POST_NOT_FOUND);
        }

        return commentRepository.getTotalComment(foundPost);
    }

    private CommentVm getCommentVM(Comment cm){
        var commentVm = new CommentVm();
        commentVm.setId(cm.getId());
        commentVm.setContent(cm.getContent());
        if (cm.getParentId() != null){
            commentVm.setParentId(cm.getParentId());
        }
        commentVm.setPostId(cm.getPost().getId());

        var user = cm.getUser();
        var userVm = new UserVm();

        userVm.setUserName(user.getUsername());
        userVm.setLastName(user.getLastName());
        userVm.setFirstName(user.getFirstName());
        userVm.setUserName(user.getUsername());
        userVm.setId(user.getId());


        commentVm.setUser(userVm);
        return commentVm;
    }

    private CommentVm getCommentVmWithChild(Comment cm){
        var commentVm = new CommentVm();
        commentVm.setId(cm.getId());
        commentVm.setContent(cm.getContent());


        var user = cm.getUser();
        var userVm = new UserVm();

        userVm.setUserName(user.getUsername());
        userVm.setLastName(user.getLastName());
        userVm.setFirstName(user.getFirstName());
        userVm.setUserName(user.getUsername());
        userVm.setId(user.getId());

        commentVm.setUser(userVm);

        // get list child

        var child = commentRepository.getListChildCommentByParent(cm.getId());

        var listChildVm = child.stream().map(this::getCommentVM).toList();


        commentVm.setChildComment(listChildVm);
        return commentVm;
    }
}
