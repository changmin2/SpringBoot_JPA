package hi.practicespring.Service;

import hi.practicespring.Domain.Board;
import hi.practicespring.Domain.Comment;
import hi.practicespring.Domain.Form.CommentForm;
import hi.practicespring.Domain.Form.WriteForm;
import hi.practicespring.repository.BoardRepository;
import hi.practicespring.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void join(Board board){
        boardRepository.save(board);
    }
    public Page<Board> allFind(Pageable pageable){
        return boardRepository.findAll(pageable);

    }
    public Board FidById(Long pageId){
        return boardRepository.getById(pageId);
    }

    public void delete(Long pageId){

        boardRepository.delete(boardRepository.getById(pageId));
    }
    @Transactional
    public void update(WriteForm writeForm){
        Board findBoard = boardRepository.getById(Long.parseLong(writeForm.getId()));
        findBoard.setTitle(writeForm.getTitle());
        findBoard.setContent(writeForm.getContent());
        findBoard.setUpdateDate(LocalDate.now());
    }
    @Transactional
    public void setComment(CommentForm commnetForm, Long pageid){
        Board findBoard = boardRepository.getById(pageid);
        Comment comment = new Comment();
        comment.setBoard(findBoard);
        comment.setCommentid(commnetForm.getReplyId());
        comment.setCommentpassword(commnetForm.getReplyPassword());
        comment.setContent(commnetForm.getReplyContent());
        comment.setUpdateDate(LocalDate.now());
        commentRepository.save(comment);
        findBoard.getCommnets().add(comment);

    }
}
