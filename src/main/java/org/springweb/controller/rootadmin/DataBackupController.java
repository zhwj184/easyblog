package org.springweb.controller.rootadmin;

import java.io.BufferedWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springweb.bean.Category;
import org.springweb.bean.Comment;
import org.springweb.bean.Post;
import org.springweb.dao.CategoryDao;
import org.springweb.dao.CommentDao;
import org.springweb.dao.PostDao;

@Controller
@RequestMapping("/rootadmin")
public class DataBackupController {

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@RequestMapping(value = "downloadCategory")
	public ModelAndView downloadCategory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String realName = "categorylist.cvs";
		String contentType = "application/octet-stream";

		downloadcategory(request, response, contentType, realName);

		return null;
	}

	@RequestMapping(value = "downloadpost")
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String realName = "postlist.cvs";
		String contentType = "application/octet-stream";

		downloadpost(request, response, contentType, realName);

		return null;
	}
	
	@RequestMapping(value = "downloadComment")
	public ModelAndView downloadComment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String realName = "commentlist.cvs";
		String contentType = "application/octet-stream";

		downloadComment(request, response, contentType, realName);

		return null;
	}
	
	public void downloadcategory(HttpServletRequest request,
			HttpServletResponse response, String contentType, String realName)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedWriter bos = null;

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("GBK"), "ISO8859-1"));
		// response.setHeader("Content-Length", String.valueOf(fileLength));

		bos = new BufferedWriter(response.getWriter());
		List<Category> categoryList = categoryDao.query(null, null);
		for (Category category : categoryList) {
			bos.append(category.getId() + "\t" + category.getParentId() + "\t"
					 + category.getGmtCreate()
					+ "\t" + category.getGmtModified() + "\t" + category.getName() + "\n");
		}
		bos.close();
	}

	public void downloadpost(HttpServletRequest request,
			HttpServletResponse response, String contentType, String realName)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedWriter bos = null;

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("GBK"), "ISO8859-1"));
		// response.setHeader("Content-Length", String.valueOf(fileLength));

		bos = new BufferedWriter(response.getWriter());
		List<Post> postList = postDao.query(null, null, null, 10000, 0);
		for (Post post : postList) {
			bos.append(post.getId() + "\t" + post.getCategoryId() + "\t"
					+ post.getParentCategoryId() + "\t" + post.getGmtCreate()
					+ "\t" + post.getGmtModified() + "\t" + post.getTitle()
					+ "\t" + post.getUrl() + "\t" + post.getView() + "\t"
					+ post.getComment() + "\t" + post.getAuthor() + "\t" + post.getContent() + "\n");
		}
		bos.close();
	}
	
	public void downloadComment(HttpServletRequest request,
			HttpServletResponse response, String contentType, String realName)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedWriter bos = null;

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("GBK"), "ISO8859-1"));
		// response.setHeader("Content-Length", String.valueOf(fileLength));

		bos = new BufferedWriter(response.getWriter());
		List<Comment> commentList = commentDao.query(null, 1000, 0);
		for (Comment comment : commentList) {
			bos.append(comment.getId() + "\t" + comment.getPostId()+ "\t" + comment.getGmtCreate()
					+ "\t" + comment.getGmtModified()+ "\t" + comment.getAuthor()+"\t" + comment.getStatus()+ "\t" + comment.getContent() + "\n");
		}
		bos.close();
	}
}
