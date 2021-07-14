package com.hackathon.service.post;

import com.hackathon.domain.dto.post.UpdatePostDto;
import com.hackathon.domain.dto.post.WritePostDto;
import com.hackathon.domain.entity.Post;
import com.hackathon.domain.entity.School;
import com.hackathon.domain.entity.User;
import com.hackathon.domain.repo.CommentRepo;
import com.hackathon.domain.repo.PostRepo;
import com.hackathon.domain.response.post.GetPostRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;

	@Override
	@Transactional
	public void writePost(WritePostDto writePostDto, User user) {
		try {
			Post post = new Post(writePostDto, user);
			postRepo.save(post);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<GetPostRO> getPosts(User user) {
		try{
			School usersSchool = user.getSchool();
			List<Post> posts = postRepo.findBySchoolOrderByUpdateAtDesc(usersSchool);

			List<GetPostRO> responsePosts = new ArrayList<>();
			for(Post post: posts){
				Long commentsNum = commentRepo.countByPost(post);
				GetPostRO responsePost = new GetPostRO(post, user, commentsNum);
				responsePosts.add(responsePost);
			}
			return responsePosts;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<GetPostRO> getPosts(User user, String postValue) {
		try{
			School usersSchool = user.getSchool();
			if(postValue == null){
				postValue = "";
			}
			postValue = "%"+postValue+"%";
			List<Post> posts = postRepo.findBySchoolAndTitleLikeOrContentLike(usersSchool.getId(), postValue);

			List<GetPostRO> responsePosts = new ArrayList<>();
			for(Post post: posts){
				Long commentsNum = commentRepo.countByPost(post);
				GetPostRO responsePost = new GetPostRO(post, user, commentsNum);
				responsePosts.add(responsePost);
			}
			return responsePosts;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	public GetPostRO getPost(Long postId, User user) {
		try{
			Post post = postRepo.findById(postId).orElseGet(()->{
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "게시글이 존재하지않습니다");
			});
			if(!post.getSchool().getId().equals(user.getSchool().getId())){
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "학교다 다름니다.");
			}
			Long commentsNum = commentRepo.countByPost(post);

			return new GetPostRO(post, user, commentsNum);
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	@Transactional
	public void updatePost(Long postId, UpdatePostDto updatePostDto, User user) {
		try{
			Post post = postRepo.findById(postId).orElseGet(()->{
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "게시글이 존재하지 않습니다.");
			});
			if(!post.getUser().getIdx().equals(user.getIdx())){
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "유저의 게시글이 아닙니다.");
			}

			post.setTitle(updatePostDto.getTitle());
			post.setContent(updatePostDto.getContent());
			post.setIsSecret(updatePostDto.getIsSecret());
			post.setUpdateAt(new Date().getTime());

			postRepo.save(post);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}

	@Override
	@Transactional
	public void removePost(Long postId, User user) {
		try {
			Post post = postRepo.findById(postId).orElseGet(() -> {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "게시글이 존재하지 않습니다.");
			});
			if (!post.getUser().getIdx().equals(user.getIdx())) {
				throw new HttpClientErrorException(HttpStatus.ACCEPTED, "유저의 게시글이 아닙니다.");
			}

			postRepo.removeByIdAndUser(postId, user);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw e;
		}
	}
}
